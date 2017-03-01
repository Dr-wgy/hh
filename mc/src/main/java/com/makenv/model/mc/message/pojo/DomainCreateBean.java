package com.makenv.model.mc.message.pojo;

/**
 * Created by wgy on 2017/3/1.
 */
public class DomainCreateBean {

    private String userid;

    private String domainid;

    private TaskDomain domain;

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

    public TaskDomain getDomain() {
        return domain;
    }

    public void setDomain(TaskDomain domain) {
        this.domain = domain;
    }
}
