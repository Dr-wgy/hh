package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;

import java.io.IOException;

/**
 * Created by alei on 2017/3/22.
 */
public class DpMetTask extends ModelTask {
  public DpMetTask(ModelStartBean modelStartBean, McConfigManager configManager) throws IOException {
    super(modelStartBean, configManager);
  }

  @Override
  protected boolean beforeHandle() {
    return true;
  }

  @Override
  protected boolean doHandle() {
    return true;
  }
}
