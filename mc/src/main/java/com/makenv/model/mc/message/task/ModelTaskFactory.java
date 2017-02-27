package com.makenv.model.mc.message.task;

import com.makenv.model.mc.enumeration.ScenarioType;

/**
 * Created by wgy on 2017/2/25.
 */
public class ModelTaskFactory {

    public static ModelTask createModelTask(ScenarioType scenarioTypeEnum){

        ModelTask modelTask = null;

        switch (scenarioTypeEnum) {

            case MODEL_BASE:

                //基准情景

                modelTask = new BaseModelTask();

                break;

            case MODEL_REDUCE:

                //减排情景

                modelTask = new ReduceModelTask();

                break;

            case MODEL_FORCAST:

                //预报情景
                modelTask = new ForcastModelTask();

                break;
        }

        return modelTask;
    }
}
