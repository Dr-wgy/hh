package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionRunDir {

  private String dirPath;
  private ScenarionRunCctm cctm;
  private ScenarionRunEmis emis;
  private ScenarionRundp dp;
  private ScenarionRunvideo video;
  private ScenarionBigScript bigscript;

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

  public ScenarionRunCctm getCctm() {
    return cctm;
  }

  public void setCctm(ScenarionRunCctm cctm) {
    this.cctm = cctm;
  }

  public ScenarionRunEmis getEmis() {
    return emis;
  }

  public void setEmis(ScenarionRunEmis emis) {
    this.emis = emis;
  }

  public ScenarionRundp getDp() {
    return dp;
  }

  public void setDp(ScenarionRundp dp) {
    this.dp = dp;
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
