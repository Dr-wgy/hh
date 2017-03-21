package com.makenv.model.mc.server.message.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wgy on 2017/2/23.
 */
public class ModelStartBean {

  private String userid; //用户编号
  private String missionid;//任务编号
  private String scenarioid;//情景编号
  private String domainid;
  private String taskid;//表示当前请求ID
  @JsonIgnoreProperties(ignoreUnknown = true)
  private String[] tasks;
  private String modelType;
  private int cores;// 计算核数
  //  private TaskDomain domain; // 模式domain的具体参数
  private ModelCommonParams common; //公共内容
  private Emis emis;
  private Wrf wrf;
  private Cmaq cmaq;

  public String[] getTasks() {
    return tasks;
  }

  public void setTasks(String[] tasks) {
    this.tasks = tasks;
  }

  public String getModelType() {
    return modelType;
  }

  public void setModelType(String modelType) {
    this.modelType = modelType;
  }

  public String getTaskid() {
    return taskid;
  }

  public void setTaskid(String taskid) {
    this.taskid = taskid;
  }

  public String getDomainid() {
    return domainid;
  }

  public void setDomainid(String domainid) {
    this.domainid = domainid;
  }

  public Cmaq getCmaq() {
    return cmaq;
  }

  public void setCmaq(Cmaq cmaq) {
    this.cmaq = cmaq;
  }


  public int getCores() {
    return cores;
  }

  public void setCores(int cores) {
    this.cores = cores;
  }

  public Emis getEmis() {
    return emis;
  }

  public void setEmis(Emis emis) {
    this.emis = emis;
  }

  public Wrf getWrf() {
    return wrf;
  }

  public void setWrf(Wrf wrf) {
    this.wrf = wrf;
  }

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

  public static class Cmaq {
    private int spinup;
    private Ic ic;
    private boolean firsttime;

    public boolean isFirsttime() {
      return firsttime;
    }

    public void setFirsttime(boolean firsttime) {
      this.firsttime = firsttime;
    }

    public Ic getIc() {
      return ic;
    }

    public void setIc(Ic ic) {
      this.ic = ic;
    }

    public int getSpinup() {
      return spinup;
    }

    public void setSpinup(int spinup) {
      this.spinup = spinup;
    }

    public static class Ic {
      private String scenarioid;
      private String date;
      private String missionid;

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

      public String getDate() {
        return date;
      }

      public void setDate(String date) {
        this.date = date;
      }
    }
  }

  public ModelCommonParams getCommon() {
    return common;
  }

  public void setCommon(ModelCommonParams common) {
    this.common = common;
  }
}
