package com.makenv.model.mc.message.task;

/**
 * Created by alei on 2017/3/8.
 */
public interface IModelTask {
  void setNextTask(IModelTask nextTask);
  void handleRequest();
}
