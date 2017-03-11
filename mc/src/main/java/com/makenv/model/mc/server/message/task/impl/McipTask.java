package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;
import com.makenv.model.mc.server.message.task.bean.McipBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alei on 2017/3/8.
 */
public class McipTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(McipTask.class);
  private McipBean mcipBean;

  public McipTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
    mcipBean = new McipBean();
  }

  protected boolean checkParams() {
    if (!super.checkParams()) {
      return false;
    }
    int spinup = modelStartBean.getCmaq().getSpinup();
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
    return true;
  }

  private void buildRenv() {
    mcipBean.setCmaq_build_path(cmaqBuildPath);
//    mcipBean.setCoordName();
//    mcipBean.setCTMLAYS();
//    mcipBean.setDebug();
//    mcipBean.setGeogrid_output_path();
//    mcipBean.setMax_dom();
//    mcipBean.setMcip_output_path();
//    mcipBean.setMcip_start_hour();
//    mcipBean.setRef_lat();
//    mcipBean.setRun_days();
//    mcipBean.setScripts_path();
//    mcipBean.setStart_date();
//    mcipBean.setWrf_output_path();
//    mcipBean.setWrf_start_hour();
  }

  @Override
  protected boolean beforeHandle() {
    return checkParams() && processDirectory();
  }

  @Override
  protected boolean doHandle() {
    return true;
  }
}
