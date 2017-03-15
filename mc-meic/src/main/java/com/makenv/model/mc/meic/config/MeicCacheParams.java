package com.makenv.model.mc.meic.config;

import com.makenv.model.mc.core.util.StringUtil;

/**
 * Created by wgy on 2017/3/13.
 */
public class MeicCacheParams {

    private int maxDom;

    private String confTemplateDir;

    private String startDate;

    private String endDate;

    private int runDays;

    private int timeDiff;//小时差

    private String emissiondir;

    private String pslist;

    private String sslist;

    private String meganPathPrefix = "";

    private String taskId;

    private boolean meganShutdown;

    private String measureJarDir;

    private String runPath;

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }

    public int getMaxDom() {
        return maxDom;
    }

    public void setMaxDom(int maxDom) {
        this.maxDom = maxDom;
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

        if(!StringUtil.isEmpty(meganPathPrefix)) {

            this.meganPathPrefix = meganPathPrefix;
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
