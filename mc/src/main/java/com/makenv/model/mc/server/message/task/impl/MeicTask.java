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

  public MeicTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  protected boolean checkParams() {
    return super.checkParams();
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
//    meicParams.setEmisFilePrefix(configManager.getSystemConfig().getModel().getMeic().getEmis_file_prefix()); deleted
    String pslist, sslist;
    if (!StringUtil.isEmpty(modelStartBean.getEmis().getActionlist())) {
      pslist = FilePathUtil.joinByDelimiter(modelStartBean.getEmis().getActionlist(), Constant.ACTIONLIST_PS);
      sslist = FilePathUtil.joinByDelimiter(modelStartBean.getEmis().getActionlist(), Constant.ACTIONLIST_SS);
      meicParams.setPslist(pslist);
      meicParams.setSslist(sslist);
    } else {
      //TODO测试一下为空是否代表无减排
//      pslist = FilePathUtil.joinByDelimiter(runDir, Constant.ACTIONLIST_PS);
//      sslist = FilePathUtil.joinByDelimiter(runDir, Constant.ACTIONLIST_SS);
//      meicParams.setPslist(pslist);
//      meicParams.setSslist(sslist);
    }
//    meicParams.setMeganPathPrefix();
    meicParams.setRunPath(runDir);
    meicParams.setTaskId(String.format("%s-%s-%s", modelStartBean.getUserid(), modelStartBean.getDomainid(), modelStartBean.getScenarioid()));
    Model model = configManager.getSystemConfig().getModel();
    meicParams.setMeicRunRequestUrl(model.getMeic().getUrl_calc_emis());
    meicParams.setMeicGetStatusUrl(model.getMeic().getUrl_get_status());
    meicParams.setFirsthour(model.getStart_hour());
    meicParams.setMeganShutdown(model.getMeic().isMeganShutdown());
    meicParams.setMeasureJarDir(meicDir);
    meicParams.setMeicCityConfigPath(modelStartBean.getEmis().getMeiccityconfig());
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
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getRenv_cmaq_sh(), params);
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
    params.put("cmaq_run_dir", runDir);
    String scriptDir = configManager.getSystemConfig().getRoot().getScript();
    params.put("meic_script", FilePathUtil.joinByDelimiter(scriptDir, configManager.getSystemConfig().getCsh().getModule_cmaq_csh()));
    params.put("renv_scrpit", renvFilePath);
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getCsh_cmaq(), params);
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
