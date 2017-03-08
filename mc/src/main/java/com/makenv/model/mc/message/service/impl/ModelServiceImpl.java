package com.makenv.model.mc.message.service.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.helper.CreateDomainHelper;
import com.makenv.model.mc.message.helper.GriddescHelper;
import com.makenv.model.mc.message.helper.TemplateFileHelper;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.service.ModelService;
import com.makenv.model.mc.message.task.IModelTask;
import com.makenv.model.mc.message.task.ModelTaskFactory;
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
    private ModelTaskFactory modelTaskFactory;
    @Autowired
    private CreateDomainHelper createDomainHelper;



  @Override
  public void startModelTask(ModelStartBean modelStartBean) {
    String[] tasks = modelStartBean.getTasks();
    IModelTask modelTask = null;
    for (int i = 0; i < tasks.length; i++) {
      if (i == 0) modelTask = modelTaskFactory.getModelTask(tasks[i]);
      else {
        modelTask.setNextTask(modelTaskFactory.getModelTask(tasks[i]));
      }
    }
//        ScenarioType scenarionTypeEnum = ScenarioType.getScenarioType(scenarioType);
//        ModelTask modelTask = ModelTaskFactory.createModelTask(scenarionTypeEnum);
//        modelTask.setModelStartBean(modelStartBean);
//        modelTask.doModelTask();

  }


        //1. 生成griddesc

  @Override
  public boolean doCreateBean(DomainCreateBean domainCreateBean) {


    //1. 生成griddesc

        //2. 生成相对应的template
        boolean nameListFlag = templateFileHelper.generateNamelist(domainCreateBean);

        //3. 执行createDomain的相关shell
        boolean succShellRunFlag = createDomainHelper.executeShell(domainCreateBean);


        boolean flag = griddescHelper.generateGriddesc(domainCreateBean);

        return flag && nameListFlag;
    }
}
