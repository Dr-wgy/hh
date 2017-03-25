package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class Wrf {

  private int spinup;
  private String lastungrib;
//  private boolean firsttime;

  public String getLastungrib() {
    return lastungrib;
  }

  public void setLastungrib(String lastungrib) {
    this.lastungrib = lastungrib;
  }

  public int getSpinup() {
    return spinup;
  }

  public void setSpinup(int spinup) {
    this.spinup = spinup;
  }
}
