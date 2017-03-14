package com.makenv.model.mc.core.bean;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicParams {

    private String meicType;

    private int maxDom;

    private String confTemplateDir;

    private String startDate;

    private int runDays;

    private int timeDiff;//小时差

    private String emissiondir;

    private String pslist;

    private String sslist;

    private String meganPathPrefix;

    private String runPath;

    private String taskId;

    private String meicRunRequestUrl;

    private String meicGetStatusUrl;

    private int onefilehours = 25;

    private int firsthour;

    private boolean meganShutdown;

    private String measureJarDir;

    private String meicCityConfigPath;

    private String controlfile;

    public String getMeicType() {
        return meicType;
    }

    public void setMeicType(String meicType) {
        this.meicType = meicType;
    }

    public String getConfTemplateDir() {
        return confTemplateDir;
    }

    public void setConfTemplateDir(String confTemplateDir) {
        this.confTemplateDir = confTemplateDir;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getRunDays() {
        return runDays;
    }

    public void setRunDays(int runDays) {
        this.runDays = runDays;
    }

    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
    }

    public String getEmissiondir() {
        return emissiondir;
    }

    public void setEmissiondir(String emissiondir) {
        this.emissiondir = emissiondir;
    }

    public String getPslist() {
        return pslist;
    }

    public void setPslist(String pslist) {
        this.pslist = pslist;
    }

    public String getSslist() {
        return sslist;
    }

    public void setSslist(String sslist) {
        this.sslist = sslist;
    }

    public String getMeganPathPrefix() {
        return meganPathPrefix;
    }

    public void setMeganPathPrefix(String meganPathPrefix) {
        this.meganPathPrefix = meganPathPrefix;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMeicRunRequestUrl() {
        return meicRunRequestUrl;
    }

    public void setMeicRunRequestUrl(String meicRunRequestUrl) {
        this.meicRunRequestUrl = meicRunRequestUrl;
    }

    public String getMeicGetStatusUrl() {
        return meicGetStatusUrl;
    }

    public void setMeicGetStatusUrl(String meicGetStatusUrl) {
        this.meicGetStatusUrl = meicGetStatusUrl;
    }

    public int getOnefilehours() {
        return onefilehours;
    }

    public void setOnefilehours(int onefilehours) {
        this.onefilehours = onefilehours;
    }

    public int getFirsthour() {
        return firsthour;
    }

    public void setFirsthour(int firsthour) {
        this.firsthour = firsthour;
    }


    public int getMaxDom() {
        return maxDom;
    }

    public void setMaxDom(int maxDom) {
        this.maxDom = maxDom;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }

    public String getMeasureJarDir() {
        return measureJarDir;
    }

    public void setMeasureJarDir(String measureJarDir) {
        this.measureJarDir = measureJarDir;
    }

    public boolean isMeganShutdown() {
        return meganShutdown;
    }

    public void setMeganShutdown(boolean meganShutdown) {
        this.meganShutdown = meganShutdown;
    }

    public String getControlfile() {
        return controlfile;
    }

    public void setControlfile(String controlfile) {
        this.controlfile = controlfile;
    }

    public String getMeicCityConfigPath() {
        return meicCityConfigPath;
    }

    public void setMeicCityConfigPath(String meicCityConfigPath) {
        this.meicCityConfigPath = meicCityConfigPath;
    }
}
