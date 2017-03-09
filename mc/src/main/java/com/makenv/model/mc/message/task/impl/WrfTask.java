package com.makenv.model.mc.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.message.pojo.ModelCommonParams;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.task.ModelTask;
import com.makenv.model.mc.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;

/**
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

  @Override
  protected boolean doHandle() {
    boolean isInitial = modelStartBean.getCommon().isInitial();
    int i = 1, j = 0;
    LocalDate _start = startDate;
    while (!_start.isAfter(endDate)) {
      i++;
      _start = _start.plusDays(1);
    }
    return true;
  }
}
