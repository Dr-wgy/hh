package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class Globaldatasets {
  private String dirPath;
  private GlobalMcip mcip;
  private GlobalMegan megan;
  private GlobalMetgrid metgrid;
  private GlobalWrf wrf;
//  private GlobalWrfDp wrfdp;

  public String getDirPath() {
    return dirPath;
  }

  public GlobalMcip getMcip() {
    return mcip;
  }

  public void setMcip(GlobalMcip mcip) {
    this.mcip = mcip;
  }

  public GlobalMegan getMegan() {
    return megan;
  }

  public void setMegan(GlobalMegan megan) {
    this.megan = megan;
  }

  public GlobalWrf getWrf() {
    return wrf;
  }

  public void setWrf(GlobalWrf wrf) {
    this.wrf = wrf;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public GlobalMetgrid getMetgrid() {
    return metgrid;
  }

  public void setMetgrid(GlobalMetgrid metgrid) {
    this.metgrid = metgrid;
  }

  public static class GlobalMcip {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }

  public static class GlobalMegan {
    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }

  public static class GlobalMetgrid {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }

}
