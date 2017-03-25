package com.makenv.model.mc.server.message.task;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.impl.CmaqTask;
import com.makenv.model.mc.server.message.task.impl.DpChemTask;
import com.makenv.model.mc.server.message.task.impl.DpMetTask;
import com.makenv.model.mc.server.message.task.impl.McipTask;
import com.makenv.model.mc.server.message.task.impl.MeganTask;
import com.makenv.model.mc.server.message.task.impl.MeicTask;
import com.makenv.model.mc.server.message.task.impl.WrfTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by alei on 2017/3/8.
 */
@Component
public class ModelTaskFactory {
  @Autowired
  private McConfigManager configManager;

  public IModelTask getModelTask(String taskType, ModelStartBean modelStartBean, String messageId) throws IOException {
    ModelTaskType _taskType = ModelTaskType.valueOf(taskType.toUpperCase());
    switch (_taskType) {
      case MEIC:
        return new MeicTask(modelStartBean, configManager, messageId);
      case WRF:
        return new WrfTask(modelStartBean, configManager, messageId);
      case MEGAN:
        return new MeganTask(modelStartBean, configManager, messageId);
      case MCIP:
        return new McipTask(modelStartBean, configManager, messageId);
      case CMAQ:
        return new CmaqTask(modelStartBean, configManager, messageId);
      case DP_MET:
        return new DpMetTask(modelStartBean, configManager, messageId);
      case DP_CHEM:
        return new DpChemTask(modelStartBean, configManager, messageId);
    }
    return null;
  }
}
