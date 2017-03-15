package com.makenv.model.mc.server.message.service.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.helper.CreateDomainHelper;
import com.makenv.model.mc.server.message.helper.GriddescHelper;
import com.makenv.model.mc.server.message.helper.TemplateFileHelper;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.service.ModelService;
import com.makenv.model.mc.server.message.task.IModelTask;
import com.makenv.model.mc.server.message.task.ModelTaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by wgy on 2017/2/23.
 */
@Service
public class ModelServiceImpl implements ModelService {
  private Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);
  @Autowired
  private McConfigManager mcConfigManager;
  @Autowired
  private GriddescHelper griddescHelper;
  @Autowired
  private TemplateFileHelper templateFileHelper;
  @Autowired
  private CreateDomainHelper createDomainHelper;
  @Autowired
  private ModelTaskFactory modelTaskFactory;


  @Override
  public boolean startModelTask(ModelStartBean modelStartBean) {
    String[] tasks = modelStartBean.getTasks();
    IModelTask firstTask = null, lastTask = null;
    for (int i = 0; i < tasks.length; i++) {
      if (i == 0) {
        firstTask = lastTask = modelTaskFactory.getModelTask(tasks[i], modelStartBean);
        if (lastTask == null) {
          logger.error(StringUtil.formatLog("invalid model task", i, tasks[i]));
          return false;
        }
      } else {
        IModelTask nextTask = modelTaskFactory.getModelTask(tasks[i], modelStartBean);
        if (lastTask == null) {
          logger.error(StringUtil.formatLog("invalid model task", i, tasks[i]));
          return false;
        }
        lastTask.setNextTask(nextTask);
        lastTask = nextTask;
      }
    }
    if (firstTask == null) {
      return false;
    }
    File modelRunFile = new File(firstTask.getModelRunFilePath());
    String content = String.format("%ssource %s%s%s\n",
        Constant.CSH_HEADER,
        mcConfigManager.getSystemConfig().getRenv().getSys());
    try {
      FileUtil.writeLocalFile(modelRunFile, content);
      modelRunFile.setExecutable(true);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    if (!firstTask.handleRequest()) {
      return false;
    }
    String errLog = String.format("%s%s%s", firstTask.getModelRunDir(), File.separator, Constant.TORQUE_LOG_ERROR);
    String infoLog = String.format("%s%s%s", firstTask.getModelRunDir(), File.separator, Constant.TORQUE_LOG_INFO);
    String qsubname = String.format("m%s-%s", modelStartBean.getUserid(), modelStartBean.getScenarioid());
    String cmd = String.format(mcConfigManager.getSystemConfig().getPbs().getQsub(), 1, mcConfigManager.getSystemConfig().getPbs().getPpn(), qsubname,
        infoLog, errLog, firstTask.getModelRunFilePath());
    logger.info(cmd);
    try {
      Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }


  //1. 生成griddesc

  @Override
  public boolean doCreateBean(DomainCreateBean domainCreateBean) {


    //1. 生成griddesc
    boolean flag = griddescHelper.generateGriddesc(domainCreateBean);

    //2. 生成相对应的template
    boolean nameListFlag = templateFileHelper.generateNamelist(domainCreateBean);

    //3. 执行createDomain的相关shell
    boolean succShellRunFlag = createDomainHelper.executeShell(domainCreateBean);

    //将domain信息生成到制定目录中
    String dirPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getDirPath();
    dirPath = dirPath.replaceAll("\\{userid\\}", domainCreateBean.getUserid()).replaceAll("\\{domainid\\}", domainCreateBean.getDomainid());
    FileUtil.checkAndMkdir(dirPath);
    try {
      FileUtil.writeLocalFile(new File(FilePathUtil.joinByDelimiter(dirPath, Constant.DOMAIN_JSON)), JacksonUtil.objToJson(domainCreateBean.getDomain()));
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return flag && nameListFlag && succShellRunFlag;
  }
}
