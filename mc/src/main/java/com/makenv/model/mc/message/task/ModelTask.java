package com.makenv.model.mc.message.task;

/**
 * Created by alei on 2017/3/8.
 */
public abstract class ModelTask implements IModelTask {
  protected IModelTask nextTask;

  public void setNextTask(IModelTask nextTask) {

  }

  public void handleRequest() {

  }
}
