package com.makenv.model.mc.server.message.task;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.config.Pbs;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by alei on 2017/3/18.
 */
public class ModelTaskHelper {
  private static Logger logger = LoggerFactory.getLogger(ModelTaskHelper.class);

  public static IModelTask buildModelTask(ModelTaskFactory mtf, ModelStartBean modelStartBean) {
    String[] tasks = modelStartBean.getTasks();
    IModelTask firstTask = null, lastTask = null;
    for (int i = 0; i < tasks.length; i++) {
      if (i == 0) {
        firstTask = lastTask = mtf.getModelTask(tasks[i], modelStartBean);
        if (lastTask == null) {
          return null;
        }
      } else {
        IModelTask nextTask = mtf.getModelTask(tasks[i], modelStartBean);
        if (lastTask == null) {
          return null;
        }
        lastTask.setNextTask(nextTask);
        lastTask = nextTask;
      }
    }
    return firstTask;
  }

  public static void buildCshWithHeader(String cshFilePath, McConfigManager configManager) throws IOException {
    String renvFile = configManager.getSystemConfig().getRenv().getSys();
    String template = configManager.getSystemConfig().getTemplate().getCsh_header();
    String content = VelocityUtil.buildTemplate(template, "sys_renv", renvFile);
    File modelRunFile = new File(cshFilePath);
    FileUtil.writeLocalFile(modelRunFile, content);
    modelRunFile.setExecutable(true);
  }

  public static void commitTask(McConfigManager configManager, ModelStartBean modelStartBean, IModelTask task) throws IOException {
    Pbs pbs = configManager.getSystemConfig().getPbs();
    int[] resource = McUtil.buildComputeResource(pbs.getPpn(), modelStartBean.getCores());
    String errLog = FilePathUtil.joinByDelimiter(task.getModelRunDir(), Constant.TORQUE_LOG_ERROR);
    String infoLog = FilePathUtil.joinByDelimiter(task.getModelRunDir(), Constant.TORQUE_LOG_INFO);
    String qsubname = String.format("m%s-%s", modelStartBean.getUserid(), modelStartBean.getScenarioid());
    String cmd = String.format(pbs.getQsub(), resource[0], resource[1], qsubname,
        infoLog, errLog, task.getModelRunFilePath());
    logger.info(cmd);
    Runtime.getRuntime().exec(cmd);
  }
}
