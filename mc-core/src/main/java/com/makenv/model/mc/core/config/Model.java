package com.makenv.model.mc.core.config;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/7.
 */
public class Model {
  private int start_hour;
  private int time_difference;
  private int wrf_run_hours;
  private String reinit_origin_date;
  private String reinit_judge_tool;
  private int reinit_cycle_days;
  private LocalDate reinitOriginDate;
//  private int days_of_reinitial;
  private int ungrib_gfs_days;
  private int debug_level;
  private String wrf_version;
  private Meic meic;

  public String getReinit_origin_date() {
    return reinit_origin_date;
  }

  public void setReinit_origin_date(String reinit_origin_date) {
    reinitOriginDate = LocalDate.parse(reinit_origin_date);
    this.reinit_origin_date = reinit_origin_date;
  }

  public String getReinit_judge_tool() {
    return reinit_judge_tool;
  }

  public void setReinit_judge_tool(String reinit_judge_tool) {
    this.reinit_judge_tool = reinit_judge_tool;
  }

  public int getReinit_cycle_days() {
    return reinit_cycle_days;
  }

  public void setReinit_cycle_days(int reinit_cycle_days) {
    this.reinit_cycle_days = reinit_cycle_days;
  }

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
    private long sleepSeconds;
    private String username;
    private String password;
    private String actionlist_header;
    private String ps_actionlist;
    private String ss_actionlist;

    public String getActionlist_header() {
      return actionlist_header;
    }

    public void setActionlist_header(String actionlist_header) {
      this.actionlist_header = actionlist_header;
    }

    public String getPs_actionlist() {
      return ps_actionlist;
    }

    public void setPs_actionlist(String ps_actionlist) {
      this.ps_actionlist = ps_actionlist;
    }

    public String getSs_actionlist() {
      return ss_actionlist;
    }

    public void setSs_actionlist(String ss_actionlist) {
      this.ss_actionlist = ss_actionlist;
    }

    public long getSleepSeconds() {
      return sleepSeconds;
    }

    public void setSleepSeconds(long sleepSeconds) {
      this.sleepSeconds = sleepSeconds;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

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

//  public int getDays_of_reinitial() {
//    return days_of_reinitial;
//  }
//
//  public void setDays_of_reinitial(int days_of_reinitial) {
//    this.days_of_reinitial = days_of_reinitial;
//  }

  public LocalDate getReinitOriginDate() {
    return reinitOriginDate;
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
