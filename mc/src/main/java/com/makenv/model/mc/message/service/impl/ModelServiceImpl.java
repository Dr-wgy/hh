package com.makenv.model.mc.message.service.impl;

import com.makenv.model.mc.enumeration.ScenarioType;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.service.ModelService;
import com.makenv.model.mc.message.task.*;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

/**
 * Created by wgy on 2017/2/23.
 */
@Service
public class ModelServiceImpl implements ModelService {


    @Override
    public void startModelTask(ModelStartBean modelStartBean) {

        String scenarioType = modelStartBean.getScenarioType();

        ScenarioType scenarioTypeEnum = ScenarioType.getScenarioType(scenarioType);

        ModelTask modelTask = ModelTaskFactory.createModelTask(scenarioTypeEnum);

        modelTask.setModelStartBean(modelStartBean);

        modelTask.doModelTask();

    }
}
