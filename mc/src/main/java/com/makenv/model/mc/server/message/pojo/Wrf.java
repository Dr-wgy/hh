package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class Wrf {

  private int spinup;
  private String lastfnl;
  private boolean firsttime;

  public boolean isFirsttime() {
    return firsttime;
  }

  public void setFirsttime(boolean firsttime) {
    this.firsttime = firsttime;
  }

  public String getLastfnl() {
    return lastfnl;
  }

  public void setLastfnl(String lastfnl) {
    this.lastfnl = lastfnl;
  }

  public int getSpinup() {
    return spinup;
  }

  public void setSpinup(int spinup) {
    this.spinup = spinup;
  }
}
