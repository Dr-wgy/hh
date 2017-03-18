package com.makenv.model.mc.server.message.body;

/**
 * Created by wgy on 2017/3/17.
 */
public class DomainMessage {

    private String userid;

    private String domainid;

    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
