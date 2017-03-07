package com.makenv.model.mc.message.service.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.enumeration.ScenarioType;
import com.makenv.model.mc.message.controller.ModelController;
import com.makenv.model.mc.message.helper.GriddescHelper;
import com.makenv.model.mc.message.helper.OceanFileHepler;
import com.makenv.model.mc.message.helper.TemplateFileHelper;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.service.ModelService;
import com.makenv.model.mc.message.task.ModelTask;
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
    private OceanFileHepler oceanFileHepler;

    @Autowired
    private TemplateFileHelper templateFileHelper;

/*    @Autowired
    private */


    @Override
    public void startModelTask(ModelStartBean modelStartBean) {
        String scenarioType = modelStartBean.getScenarioType();
        ScenarioType scenarionTypeEnum = ScenarioType.getScenarioType(scenarioType);
        ModelTask modelTask = ModelTaskFactory.createModelTask(scenarionTypeEnum);
        modelTask.setModelStartBean(modelStartBean);
        modelTask.doModelTask();

    }

    @Override
    public boolean doCreateBean(DomainCreateBean domainCreateBean) {


        //1. 生成griddesc

        boolean flag =  griddescHelper.generateGriddesc(domainCreateBean);

        //2. 生成oceanFile
        boolean oceanFileFlag = oceanFileHepler.generateOcean(domainCreateBean);


        boolean nameListFlag = templateFileHelper.generateNamelist(domainCreateBean);
        //3. 生成相对应的template

        return flag && oceanFileFlag && nameListFlag;
    }
}
