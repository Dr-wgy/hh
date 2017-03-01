package com.makenv.model.mc.message.pojo;

/**
 * Created by wgy on 2017/3/1.
 */
public class DomainCreateBean {

    private String userid;

    private String domainid;

    private TaskDomain taskDomain;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDomainid() {
        return domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    public TaskDomain getTaskDomain() {
        return taskDomain;
    }

    public void setTaskDomain(TaskDomain taskDomain) {
        this.taskDomain = taskDomain;
    }
}
