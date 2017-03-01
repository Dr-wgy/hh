package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class GlobalWrfDp {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    private String dirPath;

    public String getPathdate() {
        return pathdate;
    }

    public void setPathdate(String pathdate) {
        this.pathdate = pathdate;
    }

    public String getPathdate_domain() {
        return pathdate_domain;
    }

    public void setPathdate_domain(String pathdate_domain) {
        this.pathdate_domain = pathdate_domain;
    }

    public String getPathdate_domain_a() {
        return pathdate_domain_a;
    }

    public void setPathdate_domain_a(String pathdate_domain_a) {
        this.pathdate_domain_a = pathdate_domain_a;
    }

    public String getPathdate_domain_a_daily() {
        return pathdate_domain_a_daily;
    }

    public void setPathdate_domain_a_daily(String pathdate_domain_a_daily) {
        this.pathdate_domain_a_daily = pathdate_domain_a_daily;
    }

    public String getPathdate_domain_a_hourly() {
        return pathdate_domain_a_hourly;
    }

    public void setPathdate_domain_a_hourly(String pathdate_domain_a_hourly) {
        this.pathdate_domain_a_hourly = pathdate_domain_a_hourly;
    }

    private String pathdate;

    private String pathdate_domain;

    private String pathdate_domain_a;

    private String pathdate_domain_a_daily;

    private String pathdate_domain_a_hourly;
}
