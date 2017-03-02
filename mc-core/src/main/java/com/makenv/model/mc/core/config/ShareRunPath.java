package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ShareRunPath {
  private String dirPath;
  private RunUngrib ungrib;

  public String getDirPath() {
    return dirPath;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public RunUngrib getUngrib() {
    return ungrib;
  }

  public void setUngrib(RunUngrib ungrib) {
    this.ungrib = ungrib;
  }

  public static class RunUngrib {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }
}
