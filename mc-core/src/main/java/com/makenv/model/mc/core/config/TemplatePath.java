package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class TemplatePath {

  private String namelist_wps_geogrid;
  private String namelist_wps_metgrid;
  private String namelist_wps_ungrib;
  private String namelist_wrf;

  private String namelist_ipxwrf;

  private String namelist_oa;

  private String renv_ungrib_csh;

  private String renv_wrf_csh;

  private String renv_wrfpre_csh;

  private String griddesc;

  public String getNamelist_wps_ungrib() {
    return namelist_wps_ungrib;
  }

  public void setNamelist_wps_ungrib(String namelist_wps_ungrib) {
    this.namelist_wps_ungrib = namelist_wps_ungrib;
  }

  public String getNamelist_wps_geogrid() {
    return namelist_wps_geogrid;
  }

  public void setNamelist_wps_geogrid(String namelist_wps_geogrid) {
    this.namelist_wps_geogrid = namelist_wps_geogrid;
  }

  public String getNamelist_wps_metgrid() {
    return namelist_wps_metgrid;
  }

  public void setNamelist_wps_metgrid(String namelist_wps_metgrid) {
    this.namelist_wps_metgrid = namelist_wps_metgrid;
  }

  public String getNamelist_wrf() {
    return namelist_wrf;
  }

  public void setNamelist_wrf(String namelist_wrf) {
    this.namelist_wrf = namelist_wrf;
  }

  public String getNamelist_ipxwrf() {
    return namelist_ipxwrf;
  }

  public void setNamelist_ipxwrf(String namelist_ipxwrf) {
    this.namelist_ipxwrf = namelist_ipxwrf;
  }

  public String getNamelist_oa() {
    return namelist_oa;
  }

  public void setNamelist_oa(String namelist_oa) {
    this.namelist_oa = namelist_oa;
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
