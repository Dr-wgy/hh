package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class TemplatePath {

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

    public String getNamelist_namelist_wrf_template() {
        return namelist_namelist_wrf_template;
    }

    public void setNamelist_namelist_wrf_template(String namelist_namelist_wrf_template) {
        this.namelist_namelist_wrf_template = namelist_namelist_wrf_template;
    }

    private String namelist_wps_geogrid_template;

    private String namelist_wps_metgrid_template;

    private String namelist_namelist_wrf_template;
}
