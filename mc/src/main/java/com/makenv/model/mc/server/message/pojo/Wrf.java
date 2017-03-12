package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class Wrf {

  private int spinup;
  private String lastfnl;
  private boolean initial;

  public boolean isInitial() {
    return initial;
  }

  public void setInitial(boolean initial) {
    this.initial = initial;
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
