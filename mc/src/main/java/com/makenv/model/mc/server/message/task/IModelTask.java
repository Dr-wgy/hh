package com.makenv.model.mc.server.message.task;

/**
 * Created by alei on 2017/3/8.
 */
public interface IModelTask {
  void setNextTask(IModelTask nextTask);

  boolean handleRequest();

  String getModelRunFilePath();
}
