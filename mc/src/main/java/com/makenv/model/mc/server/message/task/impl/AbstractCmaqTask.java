package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by alei on 2017/3/11.
 */
public abstract class AbstractCmaqTask extends ModelTask {
  private Logger logger = LoggerFactory.getLogger(AbstractCmaqTask.class);

  AbstractCmaqTask(ModelStartBean modelStartBean, McConfigManager configManager) throws IOException {
    super(modelStartBean, configManager);
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
}
