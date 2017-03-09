package com.makenv.model.mc.server.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by wgy on 2017/2/24.
 */
public class VideoConvertBean {

    private String userid;

    private String messionid;

    private String scenarioid;

    @JsonProperty("domain")
    private List<String> domainList;

    private List<String> species;

    private List<Integer> height;

    private List<String> time;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessionid() {
        return messionid;
    }

    public void setMessionid(String messionid) {
        this.messionid = messionid;
    }

    public String getScenarioid() {
        return scenarioid;
    }

    public void setScenarioid(String scenarioid) {
        this.scenarioid = scenarioid;
    }

    public List<String> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<String> domainList) {
        this.domainList = domainList;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public List<Integer> getHeight() {
        return height;
    }

    public void setHeight(List<Integer> height) {
        this.height = height;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
