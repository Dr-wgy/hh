package com.makenv.model.mc.server.message.task.bean;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/23.
 */
public class WrfFnlCondBean {
  private LocalDate start;
  private LocalDate end;
  private LocalDate baseDate;
  private int reinitCycleDays;
  private boolean firsTime;
  private String wrfOutDir;
  private int reinitRunHours;

  public WrfFnlCondBean clone() {
    WrfFnlCondBean wfcb = new WrfFnlCondBean();
    wfcb.setEnd(end);
    wfcb.setStart(start);
    wfcb.setBaseDate(baseDate);
    wfcb.setFirsTime(firsTime);
    wfcb.setReinitCycleDays(reinitCycleDays);
    wfcb.setWrfOutDir(wrfOutDir);
    wfcb.setReinitRunHours(reinitRunHours);
    return wfcb;
  }

  public int getReinitRunHours() {
    return reinitRunHours;
  }

  public void setReinitRunHours(int reinitRunHours) {
    this.reinitRunHours = reinitRunHours;
  }

  public String getWrfOutDir() {
    return wrfOutDir;
  }

  public void setWrfOutDir(String wrfOutDir) {
    this.wrfOutDir = wrfOutDir;
  }

  public LocalDate getStart() {
    return start;
  }

  public void setStart(LocalDate start) {
    this.start = start;
  }

  public LocalDate getEnd() {
    return end;
  }

  public void setEnd(LocalDate end) {
    this.end = end;
  }

  public LocalDate getBaseDate() {
    return baseDate;
  }

  public void setBaseDate(LocalDate baseDate) {
    this.baseDate = baseDate;
  }

  public int getReinitCycleDays() {
    return reinitCycleDays;
  }

  public void setReinitCycleDays(int reinitCycleDays) {
    this.reinitCycleDays = reinitCycleDays;
  }

  public boolean isFirsTime() {
    return firsTime;
  }

  public void setFirsTime(boolean firsTime) {
    this.firsTime = firsTime;
  }
}
