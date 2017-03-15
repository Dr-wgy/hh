package com.makenv.model.mc.server.message.task.bean;

/**
 * Created by alei on 2017/3/10.
 */
public class McipBean {
  private String start_date;
  private int wrf_start_hour;
  private int time_difference;
  private String global;
  private int run_days;
  private String scripts_path;
  private String cmaq_build_path;
  private String geogrid_output_path;
  private String wrf_output_path;
  private String mcip_output_path;
  private String CoordName;
  private String ref_lat;
  private int max_dom;
  private int btrim;
  private String CTMLAYS;
  private int debug;
  private String cmaq_version;
  private String reinit_origin_date;
  private String reinit_judge_tool;
  private int reinit_cycle_days;

  public String getReinit_origin_date() {
    return reinit_origin_date;
  }

  public void setReinit_origin_date(String reinit_origin_date) {
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

  public String getCmaq_version() {
    return cmaq_version;
  }

  public void setCmaq_version(String cmaq_version) {
    this.cmaq_version = cmaq_version;
  }

  public String getStart_date() {
    return start_date;
  }

  public void setStart_date(String start_date) {
    this.start_date = start_date;
  }

  public int getWrf_start_hour() {
    return wrf_start_hour;
  }

  public void setWrf_start_hour(int wrf_start_hour) {
    this.wrf_start_hour = wrf_start_hour;
  }

  public int getTime_difference() {
    return time_difference;
  }

  public void setTime_difference(int time_difference) {
    this.time_difference = time_difference;
  }

  public String getGlobal() {
    return global;
  }

  public void setGlobal(String global) {
    this.global = global;
  }

  public int getRun_days() {
    return run_days;
  }

  public void setRun_days(int run_days) {
    this.run_days = run_days;
  }

  public String getScripts_path() {
    return scripts_path;
  }

  public void setScripts_path(String scripts_path) {
    this.scripts_path = scripts_path;
  }

  public String getCmaq_build_path() {
    return cmaq_build_path;
  }

  public void setCmaq_build_path(String cmaq_build_path) {
    this.cmaq_build_path = cmaq_build_path;
  }

  public String getGeogrid_output_path() {
    return geogrid_output_path;
  }

  public void setGeogrid_output_path(String geogrid_output_path) {
    this.geogrid_output_path = geogrid_output_path;
  }

  public String getWrf_output_path() {
    return wrf_output_path;
  }

  public void setWrf_output_path(String wrf_output_path) {
    this.wrf_output_path = wrf_output_path;
  }

  public String getMcip_output_path() {
    return mcip_output_path;
  }

  public void setMcip_output_path(String mcip_output_path) {
    this.mcip_output_path = mcip_output_path;
  }

  public String getCoordName() {
    return CoordName;
  }

  public void setCoordName(String coordName) {
    CoordName = coordName;
  }

  public String getRef_lat() {
    return ref_lat;
  }

  public void setRef_lat(String ref_lat) {
    this.ref_lat = ref_lat;
  }

  public int getMax_dom() {
    return max_dom;
  }

  public void setMax_dom(int max_dom) {
    this.max_dom = max_dom;
  }

  public int getBtrim() {
    return btrim;
  }

  public void setBtrim(int btrim) {
    this.btrim = btrim;
  }

  public String getCTMLAYS() {
    return CTMLAYS;
  }

  public void setCTMLAYS(String CTMLAYS) {
    this.CTMLAYS = CTMLAYS;
  }

  public int getDebug() {
    return debug;
  }

  public void setDebug(int debug) {
    this.debug = debug;
  }
}
