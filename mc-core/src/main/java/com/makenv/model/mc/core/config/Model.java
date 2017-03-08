package com.makenv.model.mc.core.config;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/7.
 */
public class Model {
  private int start_hour;
  private int local_start_hour;
  private int wrf_run_hours;
  private String base_date;
  private LocalDate baseDate;

  public LocalDate getBaseDate() {
    return baseDate;
  }

  public String getBase_date() {
    return base_date;
  }

  public void setBase_date(String base_date) {
    baseDate = LocalDate.parse(base_date);
    this.base_date = base_date;
  }

  public int getStart_hour() {
    return start_hour;
  }

  public void setStart_hour(int start_hour) {
    this.start_hour = start_hour;
  }

  public int getLocal_start_hour() {
    return local_start_hour;
  }

  public void setLocal_start_hour(int local_start_hour) {
    this.local_start_hour = local_start_hour;
  }

  public int getWrf_run_hours() {
    return wrf_run_hours;
  }

  public void setWrf_run_hours(int wrf_run_hours) {
    this.wrf_run_hours = wrf_run_hours;
  }
}
