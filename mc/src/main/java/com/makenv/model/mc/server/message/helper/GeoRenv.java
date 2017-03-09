package com.makenv.model.mc.server.message.helper;

/**
 * Created by wgy on 2017/3/8.
 */
public class GeoRenv {

    private String geog_data_path; //# geogrid输入目录

    private String geogrid_data_path; ////# geogrid输出目录

    private String namelist_wps_geogrid_template; // geogrid的namelist

    private String scripts_path; // scripts_path

    private String wrf_build_path;// wrf的安装目录

    public String getGeog_data_path() {
        return geog_data_path;
    }

    public void setGeog_data_path(String geog_data_path) {
        this.geog_data_path = geog_data_path;
    }

    public String getGeogrid_data_path() {
        return geogrid_data_path;
    }

    public void setGeogrid_data_path(String geogrid_data_path) {
        this.geogrid_data_path = geogrid_data_path;
    }

    public String getNamelist_wps_geogrid_template() {
        return namelist_wps_geogrid_template;
    }

    public void setNamelist_wps_geogrid_template(String namelist_wps_geogrid_template) {
        this.namelist_wps_geogrid_template = namelist_wps_geogrid_template;
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
}
