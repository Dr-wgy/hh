package com.makenv.model.mc.server.message.task.bean;

/**
 * Created by alei on 2017/3/11.
 */
public class MeicBean {
  private String meicType;
  private int maxDom;
  private String confTemplateDir;
  private String logFile;
  private String startDate;
  private int runDays;
  private int timeDiff;
  private String emissiondir;
  private String emisFilePrefix;
  private String taskId;
  private String pslist;
  private String sslist;
  private String meganPathPrefix;

  public String getMeicType() {
    return meicType;
  }

  public void setMeicType(String meicType) {
    this.meicType = meicType;
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

  public String getLogFile() {
    return logFile;
  }

  public void setLogFile(String logFile) {
    this.logFile = logFile;
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

  public String getEmisFilePrefix() {
    return emisFilePrefix;
  }

  public void setEmisFilePrefix(String emisFilePrefix) {
    this.emisFilePrefix = emisFilePrefix;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
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
}
