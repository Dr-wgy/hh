package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class UserRunDir {
  private String dirPath;
  private String geogrid;
  private String megan;
  private String ocean;
  private String wrf;
  private String cmaq;
  private String mcip;

  public String getMcip() {
    return mcip;
  }

  public void setMcip(String mcip) {
    this.mcip = mcip;
  }

  public String getCmaq() {
    return cmaq;
  }

  public void setCmaq(String cmaq) {
    this.cmaq = cmaq;
  }

  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public String getGeogrid() {
    return geogrid;
  }

  public void setGeogrid(String geogrid) {
    this.geogrid = geogrid;
  }

  public String getMegan() {
    return megan;
  }

  public void setMegan(String megan) {
    this.megan = megan;
  }

  public String getOcean() {
    return ocean;
  }

  public void setOcean(String ocean) {
    this.ocean = ocean;
  }

  public String getWrf() {
    return wrf;
  }

  public void setWrf(String wrf) {
    this.wrf = wrf;
  }

}
