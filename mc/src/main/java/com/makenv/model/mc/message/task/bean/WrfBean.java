package com.makenv.model.mc.message.task.bean;

import com.makenv.model.mc.core.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by wgy on 2017/3/1.
 */
public class WrfBean {

    private String namelist_wrf_template;//#模板文件绝对路径/path/namelist.wps.metgrid.template

    private String namelist_wps_metgrid_template;//#模板文件绝对路径/path/namelist.wrf.template

    private Date start_date;//开始日期

    private Date end_date;//结束日期

    private int run_days;//运行天数

    private int start_hour;//开始小时，默认18

    private int debug;//#是否为debug模式，0非，>0为debug

    private String scripts_path;//#模式驱动脚本目录

    private String wrf_build_path;//#WRF安装目录

    private String geogrid_output_path;//#GEOGRID文件输出目录

    private String ungrib_output_path;//#UNGRIB文件输出目录

    private String metgrid_output_path;//#METGRID文件输出目录

    private String real_output_path;//#REAL文件输出目录

    private String wrf_output_path;//#WRF文件输出目录

    private int restart_run;//#是否为restart run？

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


    public int getRun_days() {
        LocalDateTime startTime = TimeUtil.convertDateToLocaleDateTime(start_date);
        LocalDateTime endTime = TimeUtil.convertDateToLocaleDateTime(end_date);
        return (int)ChronoUnit.DAYS.between(startTime,endTime);
    }

    public void setRun_days(int run_days) {
        this.run_days = run_days;
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

    public String getReal_output_path() {
        return real_output_path;
    }

    public void setReal_output_path(String real_output_path) {
        this.real_output_path = real_output_path;
    }

    public String getWrf_output_path() {
        return wrf_output_path;
    }

    public void setWrf_output_path(String wrf_output_path) {
        this.wrf_output_path = wrf_output_path;
    }

    public int getRestart_run() {
        return restart_run;
    }

    public void setRestart_run(int restart_run) {
        this.restart_run = restart_run;
    }

    public String getStart_date() {

        return new SimpleDateFormat("yyyyMMdd").format(start_date);
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

}
