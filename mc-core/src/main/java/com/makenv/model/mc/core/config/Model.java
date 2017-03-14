package com.makenv.model.mc.core.config;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/7.
 */
public class Model {
  private int start_hour;
  private int time_difference;
  private int wrf_run_hours;
  private String base_date;
  private LocalDate baseDate;
  private int days_of_reinitial;
  private int ungrib_gfs_days;
  private int debug_level;
  private String wrf_version;
  private Meic meic;

  public Meic getMeic() {
    return meic;
  }

  public void setMeic(Meic meic) {
    this.meic = meic;
  }

  public static class Meic {
    private String url_calc_emis;
    private String url_get_status;
    private int onefilehours;
    private boolean meganShutdown;
    private String control_file;

    public String getUrl_calc_emis() {
      return url_calc_emis;
    }

    public void setUrl_calc_emis(String url_calc_emis) {
      this.url_calc_emis = url_calc_emis;
    }

    public String getUrl_get_status() {
      return url_get_status;
    }

    public void setUrl_get_status(String url_get_status) {
      this.url_get_status = url_get_status;
    }

    public int getOnefilehours() {
      return onefilehours;
    }

    public void setOnefilehours(int onefilehours) {
      this.onefilehours = onefilehours;
    }

    public boolean isMeganShutdown() {
      return meganShutdown;
    }

    public void setMeganShutdown(boolean meganShutdown) {
      this.meganShutdown = meganShutdown;
    }

    public String getControl_file() {
      return control_file;
    }

    public void setControl_file(String control_file) {
      this.control_file = control_file;
    }
  }

  public String getWrf_version() {
    return wrf_version;
  }

  public void setWrf_version(String wrf_version) {
    this.wrf_version = wrf_version;
  }

  public int getDebug_level() {
    return debug_level;
  }

  public void setDebug_level(int debug_level) {
    this.debug_level = debug_level;
  }

  public int getTime_difference() {
    return time_difference;
  }

  public void setTime_difference(int time_difference) {
    this.time_difference = time_difference;
  }

  public int getUngrib_gfs_days() {
    return ungrib_gfs_days;
  }

  public void setUngrib_gfs_days(int ungrib_gfs_days) {
    this.ungrib_gfs_days = ungrib_gfs_days;
  }

  public int getDays_of_reinitial() {
    return days_of_reinitial;
  }

  public void setDays_of_reinitial(int days_of_reinitial) {
    this.days_of_reinitial = days_of_reinitial;
  }

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


  public int getWrf_run_hours() {
    return wrf_run_hours;
  }

  public void setWrf_run_hours(int wrf_run_hours) {
    this.wrf_run_hours = wrf_run_hours;
  }
}
