package com.makenv.model.mc.server.message.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.config.Pbs;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.body.DomainMessage;
import com.makenv.model.mc.server.message.body.Message;
import com.makenv.model.mc.server.message.helper.CreateDomainHelper;
import com.makenv.model.mc.server.message.helper.GriddescHelper;
import com.makenv.model.mc.server.message.helper.TemplateFileHelper;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.redis.RedisQueue;
import com.makenv.model.mc.server.message.service.ModelService;
import com.makenv.model.mc.server.message.task.IModelTask;
import com.makenv.model.mc.server.message.task.ModelTaskFactory;
import com.makenv.model.mc.server.message.util.McUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

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
  @Autowired
  private RedisQueue redisQueue;

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
    String content = String.format("%ssource %s\n",
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
    Pbs pbs = mcConfigManager.getSystemConfig().getPbs();
    int[] resource = McUtil.buildComputeResource(pbs.getPpn(), modelStartBean.getCores());
    String errLog = String.format("%s%s%s", firstTask.getModelRunDir(), File.separator, Constant.TORQUE_LOG_ERROR);
    String infoLog = String.format("%s%s%s", firstTask.getModelRunDir(), File.separator, Constant.TORQUE_LOG_INFO);
    String qsubname = String.format("m%s-%s", modelStartBean.getUserid(), modelStartBean.getScenarioid());
    String cmd = String.format(pbs.getQsub(), resource[0], resource[1], qsubname,
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

    Message message = new Message();

    message.setId(UUID.randomUUID().toString());

    message.setTime(new Date());

    DomainMessage domainMessage = new DomainMessage();

    BeanUtils.copyProperties(domainCreateBean,domainMessage);

    if(flag && nameListFlag && succShellRunFlag) {

      domainMessage.setCode(0);
    }
    else {

      domainMessage.setCode(-1);
    }

    message.setBody(domainMessage);

    try {

      //发送消息
      redisQueue.sendMessgae(message);

    } catch (JsonProcessingException e) {

      logger.error("send message is failed", e);

    }
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
