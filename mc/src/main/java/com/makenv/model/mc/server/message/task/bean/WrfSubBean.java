package com.makenv.model.mc.server.message.task.bean;

import com.makenv.model.mc.core.util.LocalTimeUtil;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/23.
 */
public class WrfSubBean {
  private LocalDate start_date;//开始日期
  private int run_days;//运行天数
  private int run_hours;
  private int run_type;

  public String getStartDate() {
    return LocalTimeUtil.format(start_date, LocalTimeUtil.YMD_DATE_FORMAT);
  }

  public LocalDate getStart_date() {
    return start_date;
  }

  public void setStart_date(LocalDate start_date) {
    this.start_date = start_date;
  }

  public int getRun_days() {
    return run_days;
  }

  public void setRun_days(int run_days) {
    this.run_days = run_days;
  }

  public int getRun_hours() {
    return run_hours;
  }

  public void setRun_hours(int run_hours) {
    this.run_hours = run_hours;
  }

  public int getRun_type() {
    return run_type;
  }

  public void setRun_type(int run_type) {
    this.run_type = run_type;
  }
}
