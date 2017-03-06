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

  private String renv_ungrib_sh;

  private String renv_mcip_sh;

  private String renv_wrf_sh;

  private String griddesc;

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

  public String getNamelist_wps_ungrib() {
    return namelist_wps_ungrib;
  }

  public void setNamelist_wps_ungrib(String namelist_wps_ungrib) {
    this.namelist_wps_ungrib = namelist_wps_ungrib;
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

  public String getGriddesc() {
    return griddesc;
  }

  public void setGriddesc(String griddesc) {
    this.griddesc = griddesc;
  }

  public String getRenv_ungrib_sh() {
    return renv_ungrib_sh;
  }

  public void setRenv_ungrib_sh(String renv_ungrib_sh) {
    this.renv_ungrib_sh = renv_ungrib_sh;
  }

  public String getRenv_mcip_sh() {
    return renv_mcip_sh;
  }

  public void setRenv_mcip_sh(String renv_mcip_sh) {
    this.renv_mcip_sh = renv_mcip_sh;
  }

  public String getRenv_wrf_sh() {
    return renv_wrf_sh;
  }

  public void setRenv_wrf_sh(String renv_wrf_sh) {
    this.renv_wrf_sh = renv_wrf_sh;
  }

}
