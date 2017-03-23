package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionRunDir {

  private String dirPath;
  private ScenarionRunCctm cmaq;
  private ScenarionRunCctm mcip;
  private ScenarionRunEmis emis;
  private ScenarionRundp dp_met;
  private ScenarionRundp dp_chem;
  private ScenarionRunvideo video;
  private ScenarionBigScript bigscript;

  public ScenarionRunCctm getMcip() {
    return mcip;
  }

  public void setMcip(ScenarionRunCctm mcip) {
    this.mcip = mcip;
  }

  public ScenarionRunCctm getCmaq() {
    return cmaq;
  }

  public void setCmaq(ScenarionRunCctm cmaq) {
    this.cmaq = cmaq;
  }

  public ScenarionRundp getDp_met() {
    return dp_met;
  }

  public void setDp_met(ScenarionRundp dp_met) {
    this.dp_met = dp_met;
  }

  public ScenarionRundp getDp_chem() {
    return dp_chem;
  }

  public void setDp_chem(ScenarionRundp dp_chem) {
    this.dp_chem = dp_chem;
  }

  public ScenarionBigScript getBigscript() {
    return bigscript;
  }

  public void setBigscript(ScenarionBigScript bigscript) {
    this.bigscript = bigscript;
  }

  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public ScenarionRunEmis getEmis() {
    return emis;
  }

  public void setEmis(ScenarionRunEmis emis) {
    this.emis = emis;
  }

  public ScenarionRunvideo getVideo() {
    return video;
  }

  public void setVideo(ScenarionRunvideo video) {
    this.video = video;
  }

  public static class ScenarionRunCctm {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }
  }

  public static class ScenarionRunEmis {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

  }

  public static class ScenarionRundp {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

  }

  public static class ScenarionBigScript {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }
  }
  public static class ScenarionRunvideo {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }
  }

}
