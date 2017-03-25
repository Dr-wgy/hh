package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;

import java.io.IOException;

/**
 * Created by alei on 2017/3/8.
 */
public class MeganTask extends ModelTask {
  public MeganTask(ModelStartBean modelStartBean, McConfigManager configManager, String messageId) throws IOException {
    super(modelStartBean, configManager, messageId);
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
