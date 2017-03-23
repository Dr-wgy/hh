package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import com.makenv.model.mc.server.message.task.ModelTaskConstant;
import com.makenv.model.mc.server.message.task.bean.CmaqBean;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2017/3/8.
 */
public class CmaqTask extends AbstractCmaqTask {
  private CmaqBean cmaqBean;
  private Logger logger = LoggerFactory.getLogger(CmaqTask.class);
  private String runDir, renvPath, baseCctmDataDir, cctmDataDir, cmipOutPath, bconDataDir, meicDataDir, griddescDataDir;

  public CmaqTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  private boolean processDirectory() {
    String base = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getRun().getCmaq().getDirPath();
    runDir = processPath(base);
    runDir = String.format("%s%s%s", runDir, File.separator, System.currentTimeMillis());
    renvPath = String.format("%s%s%s", runDir, File.separator, Constant.MODEL_RENV_FILE);
    ModelStartBean.Cmaq.Ic ic = modelStartBean.getCmaq().getIc();
    if (ic != null) {
      baseCctmDataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getData().getCctm().getDirPath();
      baseCctmDataDir = baseCctmDataDir.replace("{userid}", modelStartBean.getUserid()).
          replace("{domainid}", modelStartBean.getDomainid()).
          replace("{globaldatasets}", modelStartBean.getCommon().getDatatype()).
          replace("{scenarioid}", modelStartBean.getCmaq().getIc().getScenarioid()).
          replace("{missionid}", modelStartBean.getCmaq().getIc().getMissionid());
    }
    cctmDataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getData().getCctm().getDirPath();
    cctmDataDir = processPath(cctmDataDir);
    bconDataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getData().getBcon().getDirPath();
    bconDataDir = processPath(bconDataDir);
    meicDataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getData().getEmis().getDirPath();
    meicDataDir = processPath(meicDataDir);
    griddescDataDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGriddesc().getDirPath();
    griddescDataDir = processPath(griddescDataDir);

    cmipOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMcip().getDirPath();
    cmipOutPath = processPath(cmipOutPath);
    if (modelStartBean.getCommon().getDatatype().equals(Constant.GLOBAL_TYPE_GFS)) {
      String initialTime = LocalTimeUtil.ldToUcTime(modelStartBean.getCommon().getPathdate(), configManager.getSystemConfig().getModel().getStart_hour());
      cmipOutPath = String.format("%s%s%s", cmipOutPath, File.separator, initialTime);
    }
    if (!FileUtil.checkAndMkdir(runDir)) {
      logger.error(StringUtil.formatLog("create dir failed", runDir));
      return false;
    }
    if (!FileUtil.checkAndMkdir(cctmDataDir)) {
      logger.error(StringUtil.formatLog("create dir failed", cctmDataDir));
      return false;
    }
    if (!FileUtil.checkAndMkdir(bconDataDir)) {
      logger.error(StringUtil.formatLog("create dir failed", bconDataDir));
      return false;
    }
    return true;
  }

  private void createCmaqBean() throws IOException {
    cmaqBean = new CmaqBean();
    cmaqBean.setDebug(debugLevel);
    cmaqBean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    cmaqBean.setTime_difference(timeDiff);
    cmaqBean.setRun_days((int) LocalTimeUtil.between(startDate, endDate) + 1);
    TaskDomain taskDomain = getTaskDomain();
    cmaqBean.setCmaq_version(taskDomain.getCmaq().getVersion());
    cmaqBean.setScripts_path(scriptPath);
    cmaqBean.setCmaq_build_path(cmaqBuildPath);
    cmaqBean.setGRIDDESC_path(griddescDataDir);
    cmaqBean.setMcip_output_path(cmipOutPath);
    cmaqBean.setEmis_output_path(meicDataDir);
    cmaqBean.setGlobal(modelStartBean.getCommon().getDatatype());
    String oceanPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getOcean().getDirPath();
    oceanPath = processPath(oceanPath);
    cmaqBean.setOcean_output_path(oceanPath);
    cmaqBean.setBcon_output_path(bconDataDir);
    cmaqBean.setBase_cmaq_output_path(baseCctmDataDir);
    cmaqBean.setCmaq_output_path(cctmDataDir);
    cmaqBean.setMax_dom(taskDomain.getCommon().getMax_dom());
    cmaqBean.setRun_type(modelStartBean.getCommon().isFirsttime() ? ModelTaskConstant.RUN_TYPE_INIT : ModelTaskConstant.RUN_TYPE_RESTART);
    cmaqBean.setNpcol_nprow(McUtil.buildMultiplier(modelStartBean.getCores()));
    cmaqBean.setOMI_path(configManager.getSystemConfig().getSync().getRaw_phot());
  }

  private boolean buildRenv() {
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getRenv_cmaq_sh(), "cmaqBean", cmaqBean);
    try {
      FileUtil.writeAppendLocalFileInLinux(new File(renvPath), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private boolean buildCsh() {
    Map<String, Object> params = new HashMap<>();
    params.put("cmaq_run_dir", runDir);
    params.put("cmaq_script", configManager.getSystemConfig().getCsh().getModule_cmaq_csh());
    params.put("renv_scrpit", renvPath);
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getCsh_cmaq(), params);
    try {
      FileUtil.writeAppendLocalFileInLinux(getModelRunFile(), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  protected boolean checkParams() {
    if (!super.checkParams()) {
      return false;
    }
    if (modelStartBean.getCores() <= 0) {
      logger.error(StringUtil.formatLog("cores invalid", modelStartBean.getCores()));
      return false;
    }
    return true;
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  @Override
  protected boolean doHandle() {
    try {
      createCmaqBean();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return buildRenv() && buildCsh();
  }
}
