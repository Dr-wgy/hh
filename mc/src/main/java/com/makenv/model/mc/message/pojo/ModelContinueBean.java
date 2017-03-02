package com.makenv.model.mc.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by wgy on 2017/3/1.
 */
public class ModelContinueBean {

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

    public String getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(String scenarioType) {
        this.scenarioType = scenarioType;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public TaskDomain getDomain() {
        return domain;
    }

    public void setDomain(TaskDomain domain) {
        this.domain = domain;
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

    public Cmaq getCmaq() {
        return cmaq;
    }

    public void setCmaq(Cmaq cmaq) {
        this.cmaq = cmaq;
    }

    private Cmaq cmaq;

    private List<String> successtasks;

    public List<String> getSuccesstasks() {
        return successtasks;
    }

    public void setSuccesstasks(List<String> successtasks) {
        this.successtasks = successtasks;
    }

    public static class Cmaq {

        private int spinup;

        public int getSpinup() {
            return spinup;
        }

        public void setSpinup(int spinup) {
            this.spinup = spinup;
        }
    }
}
