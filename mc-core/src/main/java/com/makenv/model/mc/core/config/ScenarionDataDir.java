package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionDataDir {

  private String dirPath;
  private ScenarioDataVideo video;
  private ScenarionDpDir dp;
  private BconDir bcon;
  private CctmDir cctm;
  private EmisDir emisDir;

  public EmisDir getEmisDir() {
    return emisDir;
  }

  public void setEmisDir(EmisDir emisDir) {
    this.emisDir = emisDir;
  }

  public CctmDir getCctm() {
    return cctm;
  }

  public void setCctm(CctmDir cctm) {
    this.cctm = cctm;
  }

  public ScenarionDpDir getDp() {
    return dp;
  }

  public void setDp(ScenarionDpDir dp) {
    this.dp = dp;
  }

  public ScenarioDataVideo getVideo() {
    return video;
  }

  public void setVideo(ScenarioDataVideo video) {
    this.video = video;
  }

  public String getDirPath() {
    return dirPath;

  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public BconDir getBcon() {
    return bcon;
  }

  public void setBcon(BconDir bcon) {
    this.bcon = bcon;
  }


  public static class EmisDir {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;
  }

  public static class BconDir {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;
  }

  public static class CctmDir {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;
  }


  public static class ScenarioDataIcon {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }
  }

  public static class ScenarioDataVideo {

    private String dirPath;

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }
  }
}
