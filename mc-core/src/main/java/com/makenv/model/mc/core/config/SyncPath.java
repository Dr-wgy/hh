package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class SyncPath {

  private String gfs;
  private String fnl;
  private String raw_phot;

  public String getRaw_phot() {
    return raw_phot;
  }

  public void setRaw_phot(String raw_phot) {
    this.raw_phot = raw_phot;
  }

  public String getGfs() {
    return gfs;
  }

  public void setGfs(String gfs) {
    this.gfs = gfs;
  }

  public String getFnl() {
    return fnl;
  }

  public void setFnl(String fnl) {
    this.fnl = fnl;
  }

}
