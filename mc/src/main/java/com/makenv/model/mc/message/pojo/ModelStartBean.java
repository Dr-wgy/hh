package com.makenv.model.mc.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by wgy on 2017/2/23.
 */
public class ModelStartBean {

    private String userid; //用户编号

    private String missionid;//任务编号

    private String scenarioid;//情景编号

    @JsonProperty("scenariotype")
    private String scenarioType;//情景类型

    private String cores;// 计算核数

    private TaskDomain domain; // 模式domain的具体参数

    private Map common; //公共内容

    private Emis emis;

    private Wrf wrf;

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public Map getCommon() {
        return common;
    }

    public void setCommon(Map common) {
        this.common = common;
    }

    public Emis getEmis() {
        return emis;
    }

    public void setEmis(Emis emis) {
        this.emis = emis;
    }

    public Wrf getWrf() {
        return wrf;
    }

    public void setWrf(Wrf wrf) {
        this.wrf = wrf;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMissionid() {
        return missionid;
    }

    public void setMissionid(String missionid) {
        this.missionid = missionid;
    }

    public String getScenarioid() {
        return scenarioid;
    }

    public void setScenarioid(String scenarioid) {
        this.scenarioid = scenarioid;
    }


    public TaskDomain getDomain() {
        return domain;
    }

    public void setDomain(TaskDomain domain) {
        this.domain = domain;
    }

    public String getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(String scenarioType) {
        this.scenarioType = scenarioType;
    }
}
