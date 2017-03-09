package com.makenv.model.mc.server.message.task;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.impl.McipTask;
import com.makenv.model.mc.server.message.task.impl.CmaqTask;
import com.makenv.model.mc.server.message.task.impl.MeganTask;
import com.makenv.model.mc.server.message.task.impl.MeicTask;
import com.makenv.model.mc.server.message.task.impl.WrfTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alei on 2017/3/8.
 */
@Component
public class ModelTaskFactory {
  @Autowired
  private McConfigManager configManager;

  public IModelTask getModelTask(String taskType, ModelStartBean modelStartBean) {
    ModelTaskType _taskType = ModelTaskType.valueOf(taskType);
    switch (_taskType) {
      case MEIC:
        return new MeicTask(modelStartBean, configManager);
      case WRF:
        return new WrfTask(modelStartBean, configManager);
      case MEGAN:
        return new MeganTask(modelStartBean, configManager);
      case MCIP:
        return new McipTask(modelStartBean, configManager);
      case CMAQ:
        return new CmaqTask(modelStartBean, configManager);
    }
    return null;
  }
}
