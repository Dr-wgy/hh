package com.makenv.model.mc.server.message.task.bean;

/**
 * Created by wgy on 2017/3/1.
 */
public class WrfBean {
  private String namelist_wrf_template;//#模板文件绝对路径/path/namelist.wps.metgrid.template
  private String namelist_wps_metgrid_template;//#模板文件绝对路径/path/namelist.wrf.template
  private String start_date;//开始日期
  private int run_days;//运行天数
  private int run_hours;
  private int start_hour;//开始小时，默认18
  private int debug;//#是否为debug模式，0非，>0为debug
  private String scripts_path;//#模式驱动脚本目录
  private String wrf_build_path;//#WRF安装目录
  private String geogrid_output_path;//#GEOGRID文件输出目录
  private String ungrib_output_path;//#UNGRIB文件输出目录
  private String metgrid_output_path;//#METGRID文件输出目录
  private String wrf_output_path;//#WRF文件输出目录
  private String base_wrf_output_path;//#WRF文件输出目录
  private int run_type;
  private String global;
  private String ungrib_file;

  public String getGlobal() {
    return global;
  }

  public void setGlobal(String global) {
    this.global = global;
  }

  public String getUngrib_file() {
    return ungrib_file;
  }

  public void setUngrib_file(String ungrib_file) {
    this.ungrib_file = ungrib_file;
  }

  public String getNamelist_wrf_template() {
    return namelist_wrf_template;
  }

  public void setNamelist_wrf_template(String namelist_wrf_template) {
    this.namelist_wrf_template = namelist_wrf_template;
  }

  public String getNamelist_wps_metgrid_template() {
    return namelist_wps_metgrid_template;
  }

  public void setNamelist_wps_metgrid_template(String namelist_wps_metgrid_template) {
    this.namelist_wps_metgrid_template = namelist_wps_metgrid_template;
  }

  public String getStart_date() {
    return start_date;
  }

  public void setStart_date(String start_date) {
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

  public int getStart_hour() {
    return start_hour;
  }

  public void setStart_hour(int start_hour) {
    this.start_hour = start_hour;
  }

  public int getDebug() {
    return debug;
  }

  public void setDebug(int debug) {
    this.debug = debug;
  }

  public String getScripts_path() {
    return scripts_path;
  }

  public void setScripts_path(String scripts_path) {
    this.scripts_path = scripts_path;
  }

  public String getWrf_build_path() {
    return wrf_build_path;
  }

  public void setWrf_build_path(String wrf_build_path) {
    this.wrf_build_path = wrf_build_path;
  }

  public String getGeogrid_output_path() {
    return geogrid_output_path;
  }

  public void setGeogrid_output_path(String geogrid_output_path) {
    this.geogrid_output_path = geogrid_output_path;
  }

  public String getUngrib_output_path() {
    return ungrib_output_path;
  }

  public void setUngrib_output_path(String ungrib_output_path) {
    this.ungrib_output_path = ungrib_output_path;
  }

  public String getMetgrid_output_path() {
    return metgrid_output_path;
  }

  public void setMetgrid_output_path(String metgrid_output_path) {
    this.metgrid_output_path = metgrid_output_path;
  }

  public String getWrf_output_path() {
    return wrf_output_path;
  }

  public void setWrf_output_path(String wrf_output_path) {
    this.wrf_output_path = wrf_output_path;
  }

  public String getBase_wrf_output_path() {
    return base_wrf_output_path;
  }

  public void setBase_wrf_output_path(String base_wrf_output_path) {
    this.base_wrf_output_path = base_wrf_output_path;
  }

  public int getRun_type() {
    return run_type;
  }

  public void setRun_type(int run_type) {
    this.run_type = run_type;
  }
}
