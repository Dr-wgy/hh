package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.bean.MeicParams;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.config.Model;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import com.makenv.model.mc.server.message.task.ModelTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2017/3/8.
 */
public class MeicTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(MeicTask.class);
  private String runDir, meicDir, confTemplateDir, renvFilePath, emisDir, jsonFilePath;
  private MeicParams meicParams;

  public MeicTask(ModelStartBean modelStartBean, McConfigManager configManager) throws IOException {
    super(modelStartBean, configManager);
  }

  protected boolean checkParams() {
    if (!super.checkParams()) {
      return false;
    }
    int spinup = modelStartBean.getCmaq().getSpinup();
    if (spinup > 0) {
      startDate = startDate.plusDays(spinup);
    }
    return true;
  }

  private boolean processDirectory() {
    String base = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getRun().getEmis().getDirPath();
    runDir = processPath(base);
    runDir = String.format("%s%s%s", runDir, File.separator, System.currentTimeMillis());
    jsonFilePath = FilePathUtil.joinByDelimiter(runDir, Constant.MEIC_JSON);
    renvFilePath = FilePathUtil.joinByDelimiter(runDir, Constant.MODEL_RENV_FILE);
    meicDir = configManager.getSystemConfig().getRoot().getMeic();
    confTemplateDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getTemplate().getDirPath();
    confTemplateDir = processPath(confTemplateDir);
    emisDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getData().getEmis().getDirPath();
    emisDir = processPath(emisDir);
    if (!FileUtil.checkAndMkdir(runDir)) {
      logger.error(StringUtil.formatLog("create dir failed", runDir));
      return false;
    }
    if (!FileUtil.checkAndMkdir(emisDir)) {
      logger.error(StringUtil.formatLog("create dir failed", emisDir));
      return false;
    }
    return true;
  }

  private void createMeicParams() throws IOException {
    TaskDomain taskDomain = getTaskDomain();
    meicParams = new MeicParams();
    meicParams.setMeicType(modelStartBean.getEmis().getCalctype());
    meicParams.setMaxDom(taskDomain.getCommon().getMax_dom());
    meicParams.setConfTemplateDir(confTemplateDir);
    meicParams.setStartDate(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    meicParams.setRunDays((int) LocalTimeUtil.between(endDate, startDate) + 1);
    meicParams.setTimeDiff(configManager.getSystemConfig().getModel().getTime_difference());
    meicParams.setEmissiondir(emisDir);
    Model model = configManager.getSystemConfig().getModel();
//    meicParams.setEmisFilePrefix(configManager.getSystemConfig().getModel().getMeic().getEmis_file_prefix()); deleted
    String pslist, sslist;
    if (!StringUtil.isEmpty(modelStartBean.getEmis().getPsal())) {
      pslist = FilePathUtil.joinByDelimiter(modelStartBean.getEmis().getPsal(), Constant.ACTIONLIST_PS);
    } else {
      pslist = FilePathUtil.joinByDelimiter(runDir, model.getMeic().getPs_actionlist());
      FileUtil.writeLocalFile(new File(pslist), model.getMeic().getActionlist_header());
    }
    if (!StringUtil.isEmpty(modelStartBean.getEmis().getSsal())) {
      sslist = FilePathUtil.joinByDelimiter(modelStartBean.getEmis().getSsal(), Constant.ACTIONLIST_SS);
    } else {
      sslist = FilePathUtil.joinByDelimiter(runDir, model.getMeic().getSs_actionlist());
      FileUtil.writeLocalFile(new File(sslist), model.getMeic().getActionlist_header());
    }
    meicParams.setPslist(pslist);
    meicParams.setSslist(sslist);

//    meicParams.setMeganPathPrefix();
    meicParams.setRunPath(runDir);
    meicParams.setTaskId(String.format("%s-%s-%s", modelStartBean.getUserid(), modelStartBean.getDomainid(), modelStartBean.getScenarioid()));
    meicParams.setMeicRunRequestUrl(model.getMeic().getUrl_calc_emis());
    meicParams.setMeicGetStatusUrl(model.getMeic().getUrl_get_status());
    int firstHour = LocalTimeUtil.minusHoursDiff(model.getTime_difference());
    meicParams.setFirsthour(firstHour);
    meicParams.setMeganShutdown(model.getMeic().isMeganShutdown());
    meicParams.setMeasureJarDir(meicDir);
    meicParams.setMeicCityConfigPath(modelStartBean.getEmis().getMeiccityconfig());
    meicParams.setSleepSeconds(model.getMeic().getSleepSeconds());
    meicParams.setUsername(model.getMeic().getUsername());
    meicParams.setPassword(model.getMeic().getPassword());
    meicParams.setControlfile(model.getMeic().getControl_file());
  }

  private boolean buildJson() {
    try {
      JacksonUtil.writeObjToJsonFile(meicParams, jsonFilePath);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private boolean buildRenv() {
    Map<String, String> params = new HashMap<>();
    params.put("jar_dir", meicDir);
    params.put("json_path", jsonFilePath);
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getRenv_meic_sh(), params);
    try {
      FileUtil.writeAppendLocalFileInLinux(new File(renvFilePath), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  private boolean buildCsh() {
    Map<String, Object> params = new HashMap<>();
    params.put("meic_run_dir", runDir);
    params.put("meic_script", configManager.getSystemConfig().getCsh().getModule_meic_csh());
    params.put("renv_scrpit", renvFilePath);
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getCsh_meic(), params);
    try {
      FileUtil.writeAppendLocalFileInLinux(getModelRunFile(), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean doHandle() {
    try {
      createMeicParams();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return buildJson() && buildRenv() && buildCsh();
  }
}
