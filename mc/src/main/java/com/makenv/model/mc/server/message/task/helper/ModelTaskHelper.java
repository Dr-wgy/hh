package com.makenv.model.mc.server.message.task.helper;

import com.makenv.model.mc.core.bean.Message;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.config.Pbs;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.enu.MessageType;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.IModelTask;
import com.makenv.model.mc.server.message.task.ModelTaskFactory;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by alei on 2017/3/18.
 */
@Component
public class ModelTaskHelper {
  private Logger logger = LoggerFactory.getLogger(ModelTaskHelper.class);
  ;
  @Autowired
  private McConfigManager configManager;

  public IModelTask buildModelTask(ModelTaskFactory mtf, ModelStartBean modelStartBean) throws IOException {
    List<String> tasks = modelStartBean.getTasks();
    if (CollectionUtils.isEmpty(tasks)) {
      String modelType = modelStartBean.getModelType();
      Map<String, Object> modelTypes = configManager.getSystemConfig().getModel().getModel_types();
      if (!modelTypes.containsKey(modelType)) {
        return null;
      }
      tasks = (List<String>) modelTypes.get(modelType);
    }
    IModelTask firstTask = null, lastTask = null;
    for (int i = 0; i < tasks.size(); i++) {
      if (i == 0) {
        firstTask = lastTask = mtf.getModelTask(tasks.get(i), modelStartBean);
        if (lastTask == null) {
          return null;
        }
      } else {
        IModelTask nextTask = mtf.getModelTask(tasks.get(i), modelStartBean);
        if (lastTask == null) {
          return null;
        }
        lastTask.setNextTask(nextTask);
        lastTask = nextTask;
      }
    }
    return firstTask;
  }

  public void buildCshWithHeader(String cshFilePath, McConfigManager configManager) throws IOException {
    String renvFile = configManager.getSystemConfig().getRenv().getSys();
    String template = configManager.getSystemConfig().getTemplate().getCsh_header();
    String content = VelocityUtil.buildTemplate(template, "sys_renv", renvFile);
    File modelRunFile = new File(cshFilePath);
    FileUtil.writeLocalFile(modelRunFile, content);
    modelRunFile.setExecutable(true);
  }

  public String commitTask(McConfigManager configManager, ModelStartBean modelStartBean, IModelTask task) throws IOException {
    Pbs pbs = configManager.getSystemConfig().getPbs();
    int[] resource = McUtil.buildComputeResource(pbs.getPpn(), modelStartBean.getCores());
    String errLog = FilePathUtil.joinByDelimiter(task.getModelRunDir(), Constant.TORQUE_LOG_ERROR);
    String infoLog = FilePathUtil.joinByDelimiter(task.getModelRunDir(), Constant.TORQUE_LOG_INFO);
    String qsubname = String.format("m%s-%s", modelStartBean.getUserid(), modelStartBean.getScenarioid());
    String cmd = String.format(pbs.getQsub(), resource[0], resource[1], qsubname,
        infoLog, errLog, task.getModelRunFilePath());
    logger.info(cmd);
    Process process = Runtime.getRuntime().exec(cmd);
    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    if ((line = stdInput.readLine()) != null) {
      return line;//作业ID
    }

    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    while ((line = stdError.readLine()) != null) {
      logger.error(line);
    }
    return null;
  }

  public static Message buildMessage(Object body, MessageType type) {
    Message msg = new Message();
    msg.setTime(new Date());
    msg.setBody(body);
    msg.setId(String.valueOf(System.currentTimeMillis()));
    msg.setType(type.id);
    return msg;
  }
}
