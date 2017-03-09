package com.makenv.model.mc.server.message.service.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.helper.CreateDomainHelper;
import com.makenv.model.mc.server.message.helper.TemplateFileHelper;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.service.ModelService;
import com.makenv.model.mc.server.message.task.IModelTask;
import com.makenv.model.mc.server.message.task.ModelTaskFactory;
import com.makenv.model.mc.server.message.helper.GriddescHelper;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return firstTask != null && firstTask.handleRequest();
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

    return flag && nameListFlag && succShellRunFlag;
  }
}
