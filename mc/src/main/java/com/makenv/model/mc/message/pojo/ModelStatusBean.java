package com.makenv.model.mc.message.pojo;

import java.util.List;

/**
 * Created by wgy on 2017/2/24.
 */
public class ModelStatusBean {

    private String userid;

    private String missionid;

    private String scenarioid;

    private List<String> status;

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

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
