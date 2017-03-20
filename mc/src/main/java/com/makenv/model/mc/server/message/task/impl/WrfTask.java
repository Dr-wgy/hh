package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import com.makenv.model.mc.server.message.task.ModelTask;
import com.makenv.model.mc.server.message.task.bean.WrfBean;
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
  private String wrfRunDir, wrfOutDir, metgridOutPath, wrfrstDir;
  private List<WrfBean> wrfBeans;
  private String renvFilePathPrefix;

  public WrfTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
    wrfBeans = new LinkedList<>();
  }

  private boolean isReInitial(LocalDate compDate) {
    LocalDate baseDate = configManager.getSystemConfig().getModel().getReinitOriginDate();
    int reinitialDays = configManager.getSystemConfig().getModel().getReinit_cycle_days();
    return LocalTimeUtil.needReInitial(baseDate, compDate, reinitialDays);
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
    wrfRunDir = String.format("%s%s%s-%s", runDir, File.separator, modelStartBean.getScenarioid(), System.currentTimeMillis());

    wrfOutDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrf().getDirPath();
    wrfOutDir = processPath(wrfOutDir);
    wrfrstDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrfrst().getDirPath();
    wrfrstDir = processPath(wrfrstDir);
    if (modelStartBean.getCommon().getDatatype().equals(Constant.GLOBAL_TYPE_GFS)) {
      String initialTime = LocalTimeUtil.ldToUcTime(modelStartBean.getCommon().getPathdate(), configManager.getSystemConfig().getModel().getStart_hour());
      wrfOutDir = String.format("%s%s%s", wrfOutDir, File.separator, initialTime);
    }
    renvFilePathPrefix = String.format("%s%s%s-", wrfRunDir, File.separator, Constant.MODEL_RENV_FILE);
    metgridOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMetgrid().getDirPath();
//    metgridOutPath = String.format("%s%s%s", metgridOutPath, File.separator, bean.getStart_date());
    metgridOutPath = processPath(metgridOutPath);
    return FileUtil.checkAndMkdir(wrfRunDir) && FileUtil.checkAndMkdir(wrfrstDir) && FileUtil.checkAndMkdir(wrfOutDir) && FileUtil.checkAndMkdir(metgridOutPath);
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  @Override
  protected boolean doHandle() {
    try {
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
    } catch (IOException e) {
      logger.error("", e);
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

  private void buildGfsRenvBean() throws IOException {
    WrfBean bean = createWrfBean();
    bean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    bean.setRun_days((int) LocalTimeUtil.between(endDate, startDate) + 1);
    bean.setRun_hours(0);
    String ungribOutPath = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath();
    ungribOutPath = String.format("%s%s%s", ungribOutPath, File.separator, bean.getStart_date());
    bean.setUngrib_output_path(ungribOutPath);
    bean.setMetgrid_output_path(metgridOutPath);
    bean.setWrf_output_path(wrfOutDir);
    wrfBeans.add(bean);
  }

  private void buildFnlRenvBean() throws IOException {
    boolean firsTime = modelStartBean.getWrf().isFirsttime();
    int i = 0, j = 0;
    LocalDate _start = LocalTimeUtil.minusHoursDiff(timeDiff, startDate);
    LocalDate _end = LocalTimeUtil.minusHoursDiff(timeDiff, endDate);
    LocalDate _current = _start, lastDate = _start;
    WrfBean bean;
    while (!_current.isAfter(_end)) {
//      if (isReInitial(_current) && i != 0) {
      if (isReInitial(_current)) {
        bean = buildFnlWrfBean(lastDate, _current);
        bean.setRun_days((int) LocalTimeUtil.between(_current, lastDate));
        bean.setRun_hours(configManager.getSystemConfig().getModel().getWrf_run_hours());
        bean.setRun_type(j++ == 0 ? (firsTime ? RUN_TYPE_INIT : RUN_TYPE_RESTART) : RUN_TYPE_REINIT);
        lastDate = _current;
        wrfBeans.add(bean);
      }
      i++;
      _current = _current.plusDays(1);
    }
    _current = _current.plusDays(-1);
    bean = buildFnlWrfBean(lastDate, _end);
    bean.setRun_hours(0);
    if (j == 0) {
      bean.setRun_type(firsTime ? RUN_TYPE_INIT : RUN_TYPE_RESTART);
      bean.setRun_days((int) LocalTimeUtil.between(_current, lastDate) + 1);
    } else {
      bean.setRun_type(RUN_TYPE_REINIT);
      bean.setRun_days((int) LocalTimeUtil.between(_current, lastDate) + 1);
    }
    wrfBeans.add(bean);
  }

  public static void main(String[] args) {
    String startDate = "20170211", endDate = "20170216";
    String reinit_origin_date = "19800101";
    int reinit_cycle_days = 5, timeDiff = 8;
    boolean firsTime = false;

    LocalDate _start = LocalTimeUtil.minusHoursDiff(timeDiff, startDate, LocalTimeUtil.YMD_DATE_FORMAT);
    LocalDate _end = LocalTimeUtil.minusHoursDiff(timeDiff, endDate, LocalTimeUtil.YMD_DATE_FORMAT);
    LocalDate _current = _start, lastDate = _start;
    LocalDate baseDate = LocalTimeUtil.parse(reinit_origin_date, LocalTimeUtil.YMD_DATE_FORMAT);
    int i = 0, j = 0;
    while (!_current.isAfter(_end)) {
      if (LocalTimeUtil.needReInitial(baseDate, _current, reinit_cycle_days)) {
        long runDays = LocalTimeUtil.between(_current, lastDate);
        int runType = (j == 0 ? (firsTime ? RUN_TYPE_INIT : RUN_TYPE_RESTART) : RUN_TYPE_REINIT);
        System.out.println(String.format("rundays=%s,runType=%s,runHours=22", runDays, runType));
        lastDate = _current;
        j++;
      }
      i++;
      _current = _current.plusDays(1);
    }
    _current = _current.plusDays(-1);
    long runDays, runType;
    if (j == 0) {
      runType = firsTime ? RUN_TYPE_INIT : RUN_TYPE_RESTART;
      runDays = LocalTimeUtil.between(_current, lastDate) + 1;
    } else {
      runType = RUN_TYPE_REINIT;
      runDays = LocalTimeUtil.between(_current, lastDate) + 1;
    }
    System.out.println(String.format("rundays=%s,runType=%s,runHours=0", runDays, runType));
  }

  private WrfBean buildFnlWrfBean(LocalDate startDate, LocalDate current) throws IOException {
    WrfBean bean = createWrfBean();
    bean.setStart_date(LocalTimeUtil.format(startDate, LocalTimeUtil.YMD_DATE_FORMAT));
    bean.setUngrib_output_path(configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath());
    String metgridOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getMetgrid().getDirPath();
    bean.setMetgrid_output_path(processPath(metgridOutPath));
    String wrfOutPath = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGlobaldatasets().getWrf().getDirPath();
    wrfOutPath = processPath(wrfOutPath);
    bean.setWrf_output_path(wrfOutPath);
    return bean;
  }


  private WrfBean createWrfBean() throws IOException {
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
    TaskDomain domain = getTaskDomain();
    bean.setWrf_version(domain.getWrf().getVersion());
    bean.setWrfrst_output_path(wrfrstDir);
    return bean;
  }
}
