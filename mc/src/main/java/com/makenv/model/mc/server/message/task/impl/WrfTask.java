package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelCommonParams;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;
import com.makenv.model.mc.server.message.task.bean.WrfBean;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * WRF根据reinitial的情况需要分段执行，另外还要考虑跨年的情况
 * Created by alei on 2017/3/8.
 */
public class WrfTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(WrfTask.class);
  private String wrfRunDir, wrfOutDir;
  private List<WrfBean> wrfBeans;
  private String renvFilePathPrefix;

  public WrfTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
    wrfBeans = new LinkedList<>();
  }

  private boolean isReInitial(LocalDate compDate) {
    LocalDate baseDate = configManager.getSystemConfig().getModel().getBaseDate();
    int reinitialDays = configManager.getSystemConfig().getModel().getDays_of_reinitial();
    return McUtil.needReInitial(baseDate, compDate, reinitialDays);
  }

  protected boolean checkParams() {
    if (!super.checkParams()) {
      return false;
    }
    int spinup = modelStartBean.getWrf().getSpinup();
    if (spinup < 0) {
      logger.error(StringUtil.formatLog("spinup invalid", spinup));
      return false;
    }
    if (spinup > 0) {
      startDate = startDate.plusDays(-spinup);
    }
    return true;
  }

  private boolean processDirectory() {
    String runDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getWrf();
    runDir = processPath(runDir);
    ModelCommonParams.TimeDate time = modelStartBean.getCommon().getTime();
    wrfRunDir = String.format("%s%s%s-%s", runDir, File.separator, modelStartBean.getScenarioid(), System.currentTimeMillis());

    wrfOutDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrf().getDirPath();
    wrfOutDir = processPath(wrfOutDir);

    if (modelStartBean.getCommon().getDatatype().equals(Constant.GLOBAL_TYPE_GFS)) {
      String initialTime = LocalTimeUtil.ldToUcTime(modelStartBean.getCommon().getPathdate(), configManager.getSystemConfig().getModel().getStart_hour());
      wrfOutDir = String.format("%s%s%s", wrfOutDir, File.separator, initialTime);
    }
    renvFilePathPrefix = String.format("%s%s%s-", wrfRunDir, File.separator, Constant.MODEL_RENV_FILE);
    return FileUtil.checkAndMkdir(wrfRunDir) && FileUtil.checkAndMkdir(wrfRunDir);
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  @Override
  protected boolean doHandle() {
    switch (modelStartBean.getCommon().getDatatype()) {
      case Constant.GLOBAL_TYPE_FNL:
        buildFnlRenvBean();
        break;
      case Constant.GLOBAL_TYPE_GFS:
        buildGfsRenvBean();
        break;
      default:
        return false;
    }

    return buildRenv() && buildCsh();
  }

  /**
   * 生成RENV文件
   */
  private boolean buildRenv() {
    String renvTemplate = configManager.getSystemConfig().getTemplate().getRenv_wrf_sh();
    try {
      for (WrfBean wrfBean : wrfBeans) {
        String content = VelocityUtil.buildTemplate(renvTemplate, "wrfBean", wrfBean);
        String renvFilePath = String.format("%s%s", renvFilePathPrefix, wrfBean.getStart_date());
        FileUtil.writeLocalFile(new File(renvFilePath), content);
      }
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private boolean buildCsh() {
    List<String> dateList = new LinkedList<>();
    for (WrfBean wrfBean : wrfBeans) {
      dateList.add(wrfBean.getStart_date());
    }
    String start_dates = String.join(" ", dateList);
    Map<String, Object> params = new HashMap<>();
    params.put("start_dates", start_dates);
    params.put("wrf_run_dir", wrfRunDir);
    params.put("wrf_script", configManager.getSystemConfig().getCsh().getModule_wrf_csh());
    params.put("renv_scrpit", renvFilePathPrefix + "${start_date}");
    String content = VelocityUtil.buildTemplate(configManager.getSystemConfig().getTemplate().getCsh_wrf(), params);
    try {
      FileUtil.writeAppendLocalFileInLinux(getModelRunFile(), content);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private void buildGfsRenvBean() {
    WrfBean bean = createWrfBean();
    bean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    bean.setRun_days((int) LocalTimeUtil.between(endDate, startDate));
    bean.setRun_hours(0);
    String ungribOutPath = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath();
    ungribOutPath = String.format("%s%s%s", ungribOutPath, File.separator, bean.getStart_date());
    bean.setUngrib_output_path(ungribOutPath);
    String metgridOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMetgrid().getDirPath();
    metgridOutPath = String.format("%s%s%s", metgridOutPath, File.separator, bean.getStart_date());
    bean.setMetgrid_output_path(processPath(metgridOutPath));
    bean.setWrf_output_path(wrfOutDir);
    wrfBeans.add(bean);
  }

  private void buildFnlRenvBean() {
    boolean isInitial = modelStartBean.getWrf().isInitial();
    int i = 0, j = 0;
    LocalDate _current = startDate, lastDate = startDate;
    WrfBean bean;
    while (!_current.isAfter(endDate)) {
      if (isReInitial(_current) && i != 0) {
        bean = buildFnlWrfBean(lastDate, _current);
        wrfBeans.add(bean);
        bean.setRun_type(j++ == 0 ? (isInitial ? RUN_TYPE_INIT : RUN_TYPE_RESTART) : RUN_TYPE_REINIT);
        lastDate = _current;
      }
      i++;
      _current = _current.plusDays(1);
    }
    bean = buildFnlWrfBean(lastDate, endDate);
    if (j == 0) {
      bean.setRun_type(isInitial ? RUN_TYPE_INIT : RUN_TYPE_RESTART);
    } else {
      bean.setRun_type(RUN_TYPE_REINIT);
    }
    wrfBeans.add(bean);
  }

  private WrfBean buildFnlWrfBean(LocalDate startDate, LocalDate current) {
    WrfBean bean = createWrfBean();
    bean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    bean.setRun_days((int) LocalTimeUtil.between(current, startDate));
    bean.setRun_hours(configManager.getSystemConfig().getModel().getWrf_run_hours());
    bean.setUngrib_output_path(configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath());
    String metgridOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMetgrid().getDirPath();
    bean.setMetgrid_output_path(processPath(metgridOutPath));
    String wrfOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrf().getDirPath();
    wrfOutPath = processPath(wrfOutPath);
    bean.setWrf_output_path(wrfOutPath);
    return bean;
  }


  private WrfBean createWrfBean() {
    WrfBean bean = new WrfBean();
    bean.setNamelist_wps_metgrid_template(metgridTemplate);
    bean.setNamelist_wrf_template(wrfTemplate);
    bean.setStart_hour(startHour);
    bean.setScripts_path(scriptPath);
    bean.setWrf_build_path(wrfBuildPath);
    bean.setGeogrid_output_path(geogridOutputPath);
    bean.setDebug(debugLevel);
    bean.setGlobal(modelStartBean.getCommon().getDatatype());
    bean.setUngrib_file(Constant.UNGRIB_FILE_PREFIX);
    return bean;
  }
}
