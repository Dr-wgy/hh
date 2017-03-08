package com.makenv.model.mc.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.task.ModelTask;

/**
 * Created by alei on 2017/3/8.
 */
public class MeicTask extends ModelTask {
  public MeicTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  @Override
  protected boolean beforeHandle() {
    return false;
  }

  @Override
  protected boolean doHandle() {
    return false;
  }
}
