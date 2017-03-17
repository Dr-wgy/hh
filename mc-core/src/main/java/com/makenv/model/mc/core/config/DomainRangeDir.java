package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class DomainRangeDir {
  private String dirPath;
  private UserCommonDir common;
  private MissionDir missionid;
//  private String modelRunPath;
//
//  public String getModelRunPath() {
//    return modelRunPath;
//  }
//
//  public void setModelRunPath(String modelRunPath) {
//    this.modelRunPath = modelRunPath;
//  }

  public MissionDir getMissionid() {
    return missionid;
  }

  public void setMissionid(MissionDir missionid) {
    this.missionid = missionid;
  }

  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public UserCommonDir getCommon() {
    return common;
  }

  public void setCommon(UserCommonDir common) {
    this.common = common;
  }
}
