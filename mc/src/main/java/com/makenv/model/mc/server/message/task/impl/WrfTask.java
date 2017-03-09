package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;
import com.makenv.model.mc.server.message.util.McUtil;
import com.makenv.model.mc.server.message.pojo.ModelCommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

  private int getRunType(LocalDate compDate) {
    LocalDate baseDate = configManager.getSystemConfig().getModel().getBaseDate();
    int reinitialDays = configManager.getSystemConfig().getModel().getDays_of_reinitial();
    if (McUtil.needReInitial(baseDate, compDate, reinitialDays)) {
      return RUN_TYPE_INIT;
    }
    return 1;
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


  private Map<String, Object> buildRenvParams(LocalDate start, LocalDate end,int runType) {
    Map<String, Object> params = createParams();
    params.put("start_date", LocalTimeUtil.format(start,LocalTimeUtil.YMD_DATE_FORMAT));
    params.put("run_days", LocalTimeUtil.between(start,end));
    params.put("run_hours", "");
    params.put("ungrib_output_path", "");
    params.put("metgrid_output_path", "");
    params.put("wrf_output_path", "");
    params.put("base_wrf_output_path", "");
    params.put("run_type",runType);
    return params;
  }

  @Override
  protected boolean doHandle() {
    boolean isInitial = modelStartBean.getCommon().isInitial();
    int i = 1, j = 0;
    LocalDate _start = startDate;
    while (!_start.isAfter(endDate)) {

      if(_start.isEqual(endDate)){

      }else {

      }
      i++;
      _start = _start.plusDays(1);
    }
    return true;
  }

  private Map<String, Object> createParams() {
    Map<String, Object> params = new HashMap<>();
    params.put("namelist_wps_metgrid_template", metgridTemplate);
    params.put("namelist_wrf_template", wrfTemplate);
    params.put("start_hour", startHour);
    params.put("scripts_path", scriptPath);
    params.put("wrf_build_path", wrfBuildPath);
    params.put("geogrid_output_path", geogridOutputPath);
    params.put("debug", Constant.MODEL_DEBUG_LEVEL);
    return params;
  }
}
