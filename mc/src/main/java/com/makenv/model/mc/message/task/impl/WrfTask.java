package com.makenv.model.mc.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.task.ModelTask;
import com.makenv.model.mc.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/8.
 */
public class WrfTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(WrfTask.class);

  public WrfTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  private int getRunType(LocalDate compDate) {
    LocalDate baseDate = configManager.getSystemConfig().getModel().getBaseDate();
    if (McUtil.needReInitial(baseDate, compDate)) {
      return 0;
    }
    return 1;
  }

  @Override
  protected boolean beforeHandle() {
    String runDir = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getWrf();
    runDir = processPath(runDir);
    FileUtil.checkAndMkdir(runDir);

    LocalDate start = LocalTimeUtil.parse(modelStartBean.getCommon().getTime().getStart(), LocalTimeUtil.YMD_DATE_FORMAT);
    LocalDate end = LocalTimeUtil.parse(modelStartBean.getCommon().getTime().getEnd(), LocalTimeUtil.YMD_DATE_FORMAT);
    if (start.isAfter(end)) {
      logger.error("common.time invalid");
      return false;
    }
    return false;
  }

  @Override
  protected boolean doHandle() {
    return false;
  }
}
