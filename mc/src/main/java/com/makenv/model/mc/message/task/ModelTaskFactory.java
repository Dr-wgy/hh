package com.makenv.model.mc.message.task;

import com.makenv.model.mc.message.task.impl.CmaqTask;
import com.makenv.model.mc.message.task.impl.McipTask;
import com.makenv.model.mc.message.task.impl.MeganTask;
import com.makenv.model.mc.message.task.impl.MeicTask;
import com.makenv.model.mc.message.task.impl.WrfTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alei on 2017/3/8.
 */
@Component
public class ModelTaskFactory {
  @Autowired
  private WrfTask wrfTask;
  @Autowired
  private MeicTask meicTask;
  @Autowired
  private MeganTask meganTask;
  @Autowired
  private McipTask mcipTask;
  @Autowired
  private CmaqTask cmaqTask;

  public IModelTask getModelTask(String taskType) {
    ModelTaskType _taskType = ModelTaskType.valueOf(taskType);
    switch (_taskType) {
      case MEIC:
        return meicTask;
      case WRF:
        return wrfTask;
      case MEGAN:
        return meganTask;
      case MCIP:
        return mcipTask;
      case CMAQ:
        return cmaqTask;
    }
    return null;
  }
}
