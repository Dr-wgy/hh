package com.makenv.model.mc.message.handler.wrf;

/**
 * Created by wgy on 2017/3/1.
 */
public class WrfPreBean {

    private String namelist_wps_geogrid_template; //geogrid模板文件绝对路径

    private String namelist_wps_metgrid_template; //Metgrid模板文件绝对路径

    private String namelist_wrf_template; //WRF模板文件绝对路径

    private String start_date; // 开始日期

    private String end_date; // 结束日期

    private String start_hour;// 开始小时

    private String geog_data; //执行geogrid输入文件所需要的数据目录

    private String geogrid_data; // 输出目录/path/geogrid

    private String ungrib_fnl_data; //输出目录/path/ungrib/

    private String ungrib_gfs_data; //ungrib gfs输出目录/path/ungrib/

    private String real_data;//real_data目录/path/real_data

    private int debug;//是否为debug模式，0非，>0为debug

    public String getNamelist_wps_geogrid_template() {
        return namelist_wps_geogrid_template;
    }

    public void setNamelist_wps_geogrid_template(String namelist_wps_geogrid_template) {
        this.namelist_wps_geogrid_template = namelist_wps_geogrid_template;
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

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGeog_data() {
        return geog_data;
    }

    public void setGeog_data(String geog_data) {
        this.geog_data = geog_data;
    }

    public String getGeogrid_data() {
        return geogrid_data;
    }

    public void setGeogrid_data(String geogrid_data) {
        this.geogrid_data = geogrid_data;
    }

    public String getUngrib_fnl_data() {
        return ungrib_fnl_data;
    }

    public void setUngrib_fnl_data(String ungrib_fnl_data) {
        this.ungrib_fnl_data = ungrib_fnl_data;
    }

    public String getUngrib_gfs_data() {
        return ungrib_gfs_data;
    }

    public void setUngrib_gfs_data(String ungrib_gfs_data) {
        this.ungrib_gfs_data = ungrib_gfs_data;
    }

    public String getReal_data() {
        return real_data;
    }

    public void setReal_data(String real_data) {
        this.real_data = real_data;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getNamelist_wrf_template() {
        return namelist_wrf_template;
    }

    public void setNamelist_wrf_template(String namelist_wrf_template) {
        this.namelist_wrf_template = namelist_wrf_template;
    }
}
