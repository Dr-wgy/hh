package com.makenv.model.mc.message.task;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.pojo.ModelStartBean;

/**
 * Created by alei on 2017/3/8.
 */
public abstract class ModelTask implements IModelTask {
  protected IModelTask nextTask;
  protected ModelStartBean modelStartBean;
  protected McConfigManager configManager;

  public ModelTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    this.modelStartBean = modelStartBean;
    this.configManager = configManager;
  }

  public void setNextTask(IModelTask nextTask) {
    this.nextTask = nextTask;
  }

  public boolean handleRequest() {
    if (!beforeHandle()) return false;
    if (!doHandle()) return false;
    return afterHandle();
  }

  protected abstract boolean beforeHandle();

  protected abstract boolean doHandle();

  protected boolean afterHandle() {
    if (nextTask != null) return nextTask.handleRequest();
    return true;
  }

}
