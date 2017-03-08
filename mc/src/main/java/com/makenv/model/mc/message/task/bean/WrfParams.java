package com.makenv.model.mc.message.task.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wgy on 2017/3/3.
 */
public class WrfParams {

    @JsonFormat(pattern = "yyyyMMdd")
    @DateTimeFormat(pattern="yyyyMMdd")
    private Date start_date;
    @JsonFormat(pattern = "yyyyMMdd")
    @DateTimeFormat(pattern="yyyyMMdd")
    private Date end_date;

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    private int start_hour;

    private String domainid;

    private String userid;

    private String globaldatasets;

    private Date pathdate;

    private long cores;

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public String getDomainid() {
        return domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGlobaldatasets() {
        return globaldatasets;
    }

    public void setGlobaldatasets(String globaldatasets) {
        this.globaldatasets = globaldatasets;
    }

    public Date getPathdate() {
        return pathdate;
    }

    public void setPathdate(Date pathdate) {
        this.pathdate = pathdate;
    }

    public long getCores() {
        return cores;
    }

    public void setCores(long cores) {
        this.cores = cores;
    }

}
