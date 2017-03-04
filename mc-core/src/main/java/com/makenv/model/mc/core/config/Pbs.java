package com.makenv.model.mc.core.config;

/**
 * Created by alei on 2017/3/4.
 */
public class Pbs {
  private int ppn;
  private String queue;
  private String qsub;

  public int getPpn() {
    return ppn;
  }

  public void setPpn(int ppn) {
    this.ppn = ppn;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public String getQsub() {
    return qsub;
  }

  public void setQsub(String qsub) {
    this.qsub = qsub;
  }
}
