package com.makenv.model.mc.server.enumeration;

/**
 * Created by wgy on 2017/2/23.
 */
public enum ScenarioType {

    MODEL_FORCAST("forcast"),

    MODEL_REDUCE("reduce"),

    MODEL_BASE("base");

    ScenarioType(String scenarioType) {

        this.scenarioType = scenarioType;
    };

    //forcast|reduce|base

    public String getScenarioType() {
        return scenarioType;
    }

    private String scenarioType;

    public static  ScenarioType  getScenarioType(String selfScenarioType){

        ScenarioType[] scenarioTypes =  ScenarioType.values();

        for(ScenarioType scenarioType:scenarioTypes) {

            if(scenarioType.getScenarioType().equalsIgnoreCase(selfScenarioType)){

                return scenarioType;
            };

        }
        return null;
    }
}
