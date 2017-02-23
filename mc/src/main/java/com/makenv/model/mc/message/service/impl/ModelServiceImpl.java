package com.makenv.model.mc.message.service.impl;

import com.makenv.model.mc.enumeration.ScenarioType;
import com.makenv.model.mc.message.pojo.ModelStartBean;
import com.makenv.model.mc.message.service.ModelService;
import com.makenv.model.mc.message.task.BaseModelTask;
import com.makenv.model.mc.message.task.ForcastModelTask;
import com.makenv.model.mc.message.task.ModelTask;
import com.makenv.model.mc.message.task.ReduceModelTask;
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

        ModelTask modelTask = null;

        switch (scenarioTypeEnum) {

            case MODEL_BASE:

                //基准情景

                modelTask = new BaseModelTask(modelStartBean);

                break;

            case MODEL_REDUCE:

                //减排情景

                modelTask = new ReduceModelTask(modelStartBean);

                break;

            case MODEL_FORCAST:

                //预报情景
                modelTask = new ForcastModelTask(modelStartBean);

                break;
        }

        modelTask.doModelTask();

    }
}
