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
  protected final static int RUN_TYPE_INIT = 0;
  protected final static int RUN_TYPE_RESTART = 1;
  protected final static int RUN_TYPE_REINIT = 2;

  public ModelTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    this.modelStartBean = modelStartBean;
    this.configManager = configManager;
  }

  protected String processPath(String path) {
    return path.replace("\\{userid\\}", modelStartBean.getUserid()).
        replace("\\{domainid\\}", modelStartBean.getDomainid()).
        replace("\\{globaldatasets\\}", modelStartBean.getCommon().getDatatype());
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
