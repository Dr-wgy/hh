package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.pojo.ModelCommonParams;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;
import com.makenv.model.mc.server.message.task.bean.WrfBean;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * WRF根据reinitial的情况需要分段执行，另外还要考虑跨年的情况
 * Created by alei on 2017/3/8.
 */
public class WrfTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(WrfTask.class);
  private String wrfRunDir;
  private LocalDate startDate, endDate;

  public WrfTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  private boolean isReInitial(LocalDate compDate) {
    LocalDate baseDate = configManager.getSystemConfig().getModel().getBaseDate();
    int reinitialDays = configManager.getSystemConfig().getModel().getDays_of_reinitial();
    return McUtil.needReInitial(baseDate, compDate, reinitialDays);
  }

  private boolean checkParams() {
    String start = modelStartBean.getCommon().getTime().getStart();
    String end = modelStartBean.getCommon().getTime().getEnd();
    startDate = LocalTimeUtil.parse(start, LocalTimeUtil.YMD_DATE_FORMAT);
    endDate = LocalTimeUtil.parse(end, LocalTimeUtil.YMD_DATE_FORMAT);
    if (startDate.isAfter(endDate)) {
      logger.error(StringUtil.formatLog("common.time invalid", start, end));
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
    wrfRunDir = String.format("%s%s%s-%s-%s", runDir, File.separator, time.getStart(), time.getEnd(), modelStartBean.getWrf().getSpinup());
    return FileUtil.checkAndMkdir(wrfRunDir);
  }

  @Override
  protected boolean beforeHandle() {
    if (!checkParams()) return false;
    super.beforeHandle();
    return processDirectory();
  }


  private List<WrfBean> buildRenvBean() {
    boolean isInitial = modelStartBean.getCommon().isInitial();
    int i = 0, j = 0;
    LocalDate _current = startDate, lastDate = startDate;
    TreeMap<String, Integer> renInitialData = new TreeMap<>();
    WrfBean bean;
    List<WrfBean> beans = new ArrayList<>();
    while (!_current.isAfter(endDate)) {
      if (isReInitial(_current)) {
        if (i != 0) {
          bean = createWrfBean();
          beans.add(bean);
          bean.setStart_date(LocalTimeUtil.format(lastDate, LocalTimeUtil.YMD_DATE_FORMAT));
//          bean.setRun_days();
          bean.setRun_hours(configManager.getSystemConfig().getModel().getWrf_run_hours());
//          bean.setUngrib_output_path();
//          bean.setMetgrid_output_path();
//          bean.setWrf_output_path();
//          bean.setBase_wrf_output_path();
          bean.setRun_type(j == 0 ? RUN_TYPE_INIT : RUN_TYPE_REINIT);
          lastDate = _current;
          j++;
        }
      }
      i++;
      _current = _current.plusDays(1);
    }
//    params.put("start_date", LocalTimeUtil.format(start,LocalTimeUtil.YMD_DATE_FORMAT));
//    params.put("run_days", LocalTimeUtil.between(start,end));
//    params.put("run_hours", "");
//    params.put("ungrib_output_path", "");
//    params.put("metgrid_output_path", "");
//    params.put("wrf_output_path", "");
//    params.put("base_wrf_output_path", "");
//    params.put("run_type",runType);
    return null;
  }

  @Override
  protected boolean doHandle() {
    return true;
  }

  private WrfBean createWrfBean() {
    WrfBean bean = new WrfBean();
    bean.setNamelist_wps_metgrid_template(metgridTemplate);
    bean.setNamelist_wrf_template(wrfTemplate);
    bean.setStart_hour(startHour);
    bean.setScripts_path(scriptPath);
    bean.setWrf_build_path(wrfBuildPath);
    bean.setGeogrid_output_path(geogridOutputPath);
    bean.setDebug(Constant.MODEL_DEBUG_LEVEL);
    return bean;
  }
}
