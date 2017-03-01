package com.makenv.model.mc.message.service.impl;

import com.makenv.model.mc.enumeration.ScenarioType;
import com.makenv.model.mc.message.handler.GeneateGriddescHandler;
import com.makenv.model.mc.message.handler.GenerateOceanFileHandler;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.service.ModelService;
import com.makenv.model.mc.message.task.ModelTask;
import com.makenv.model.mc.message.task.ModelTaskFactory;
import org.springframework.stereotype.Service;

/**
 * Created by wgy on 2017/2/23.
 */
@Service
public class ModelServiceImpl implements ModelService {


    @Override
    public void startModelTask(ModelStartBean modelStartBean) {

        String scenarioType = modelStartBean.getScenarioType();

        ScenarioType scenarionTypeEnum = ScenarioType.getScenarioType(scenarioType);

        ModelTask modelTask = ModelTaskFactory.createModelTask(scenarionTypeEnum);

        modelTask.setModelStartBean(modelStartBean);

        modelTask.doModelTask();

    }

    @Override
    public void doCreateBean(DomainCreateBean domainCreateBean) {

        GeneateGriddescHandler geneateGriddescHandler =  new GeneateGriddescHandler();

        GenerateOceanFileHandler generateOceanFileHandler = new GenerateOceanFileHandler();


    }
}
