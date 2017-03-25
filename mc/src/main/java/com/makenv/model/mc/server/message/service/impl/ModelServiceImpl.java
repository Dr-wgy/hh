package com.makenv.model.mc.server.message.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.makenv.model.mc.core.bean.Message;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.constant.ResponseConstant;
import com.makenv.model.mc.core.enu.MessageType;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.body.StartModelResultMessage;
import com.makenv.model.mc.server.message.helper.CreateDomainHelper;
import com.makenv.model.mc.server.message.helper.GriddescHelper;
import com.makenv.model.mc.server.message.helper.TemplateFileHelper;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.redis.RedisQueue;
import com.makenv.model.mc.server.message.service.ModelService;
import com.makenv.model.mc.server.message.task.IModelTask;
import com.makenv.model.mc.server.message.task.ModelTaskFactory;
import com.makenv.model.mc.server.message.task.helper.ModelTaskHelper;
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
  @Autowired
  private RedisQueue redisQueue;
  @Autowired
  private ModelTaskHelper modelTaskHelper;

  private void sendStartModelResultMessage(int code, String date) {
    StartModelResultMessage body = new StartModelResultMessage();
    body.setCode(code);
    body.setDate(date);
    body.setDesc(ResponseConstant.getDescription(code));
    Message message = ModelTaskHelper.buildMessage(body, MessageType.MB_START_MODEL_RESULT);
    try {
      redisQueue.sendMessage(message);
    } catch (JsonProcessingException e) {
      logger.error("", e);
    }
  }

  @Override
  public boolean startModelTask(ModelStartBean modelStartBean, String messageId) {
    IModelTask firstTask;
    try {
      firstTask = modelTaskHelper.buildModelTask(modelTaskFactory, modelStartBean, messageId);
      if (firstTask == null) {
        logger.error(StringUtil.formatLog("invalid model task", modelStartBean.getModelType()));
        sendStartModelResultMessage(ResponseConstant.ERR_PARAMS, modelStartBean.getCommon().getTime().getStart());
        return false;
      }
    } catch (IOException e) {
      logger.error("", e);
      sendStartModelResultMessage(ResponseConstant.ERR_PARAMS, modelStartBean.getCommon().getTime().getStart());
      return false;
    }
    try {
      modelTaskHelper.buildCshWithHeader(firstTask.getModelRunFilePath(), mcConfigManager);
    } catch (IOException e) {
      logger.error("", e);
      sendStartModelResultMessage(ResponseConstant.ERR_SYS, modelStartBean.getCommon().getTime().getStart());
      return false;
    }
    if (!firstTask.handleRequest()) {
      sendStartModelResultMessage(ResponseConstant.ERR_PARAMS, modelStartBean.getCommon().getTime().getStart());
      return false;
    }
    try {
      modelTaskHelper.commitTask(mcConfigManager, modelStartBean, firstTask);
    } catch (IOException e) {
      logger.error("", e);
      sendStartModelResultMessage(ResponseConstant.ERR_COMMIT_JOB, modelStartBean.getCommon().getTime().getStart());
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
