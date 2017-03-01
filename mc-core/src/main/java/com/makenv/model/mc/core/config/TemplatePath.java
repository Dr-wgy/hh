package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class TemplatePath {

    private String namelist_wps_geogrid_template;

    private String namelist_wps_metgrid_template;


    private String namelist_wrf_template;

    private String namelist_ipxwrf_template;

    private String namelist_oa_template;

    private String renv_ungrib_csh;

    private String renv_wrf_csh;

    private String renv_wrfpre_csh;

    private String griddesc;

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

    public String getNamelist_wrf_template() {
        return namelist_wrf_template;
    }

    public void setNamelist_wrf_template(String namelist_wrf_template) {
        this.namelist_wrf_template = namelist_wrf_template;
    }

    public String getNamelist_ipxwrf_template() {
        return namelist_ipxwrf_template;
    }

    public void setNamelist_ipxwrf_template(String namelist_ipxwrf_template) {
        this.namelist_ipxwrf_template = namelist_ipxwrf_template;
    }

    public String getNamelist_oa_template() {
        return namelist_oa_template;
    }

    public void setNamelist_oa_template(String namelist_oa_template) {
        this.namelist_oa_template = namelist_oa_template;
    }

    public String getRenv_ungrib_csh() {
        return renv_ungrib_csh;
    }

    public void setRenv_ungrib_csh(String renv_ungrib_csh) {
        this.renv_ungrib_csh = renv_ungrib_csh;
    }

    public String getRenv_wrf_csh() {
        return renv_wrf_csh;
    }

    public void setRenv_wrf_csh(String renv_wrf_csh) {
        this.renv_wrf_csh = renv_wrf_csh;
    }

    public String getRenv_wrfpre_csh() {
        return renv_wrfpre_csh;
    }

    public void setRenv_wrfpre_csh(String renv_wrfpre_csh) {
        this.renv_wrfpre_csh = renv_wrfpre_csh;
    }

    public String getGriddesc() {
        return griddesc;
    }

    public void setGriddesc(String griddesc) {
        this.griddesc = griddesc;
    }
}
