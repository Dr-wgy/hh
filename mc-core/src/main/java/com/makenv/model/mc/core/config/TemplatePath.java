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

  private String meic_cache_conf_vm;
  private String meic_server_conf_vm;

  private String renv_ungrib_sh;
  private String renv_domain_sh;
  private String renv_wrf_sh;
  private String renv_mcip_sh;
  private String renv_megan_sh;
  private String renv_meic_sh;
  private String renv_cmaq_sh;

  private String griddesc;

  private String csh_wrf;
  private String csh_mcip;
  private String csh_meic;
  private String csh_megan;
  private String csh_cmaq;
  private String csh_header;

  public String getCsh_header() {
    return csh_header;
  }

  public void setCsh_header(String csh_header) {
    this.csh_header = csh_header;
  }

  private String meic_jar_conf;

  public String getCsh_mcip() {
    return csh_mcip;
  }

  public void setCsh_mcip(String csh_mcip) {
    this.csh_mcip = csh_mcip;
  }

  public String getCsh_meic() {
    return csh_meic;
  }

  public void setCsh_meic(String csh_meic) {
    this.csh_meic = csh_meic;
  }

  public String getCsh_megan() {
    return csh_megan;
  }

  public void setCsh_megan(String csh_megan) {
    this.csh_megan = csh_megan;
  }

  public String getCsh_cmaq() {
    return csh_cmaq;
  }

  public void setCsh_cmaq(String csh_cmaq) {
    this.csh_cmaq = csh_cmaq;
  }

  public String getCsh_wrf() {
    return csh_wrf;
  }

  public void setCsh_wrf(String csh_wrf) {
    this.csh_wrf = csh_wrf;
  }

  public String getRenv_domain_sh() {
    return renv_domain_sh;
  }

  public void setRenv_domain_sh(String renv_domain_sh) {
    this.renv_domain_sh = renv_domain_sh;
  }

  public String getRenv_megan_sh() {
    return renv_megan_sh;
  }

  public void setRenv_megan_sh(String renv_megan_sh) {
    this.renv_megan_sh = renv_megan_sh;
  }

  public String getRenv_meic_sh() {
    return renv_meic_sh;
  }

  public void setRenv_meic_sh(String renv_meic_sh) {
    this.renv_meic_sh = renv_meic_sh;
  }

  public String getRenv_cmaq_sh() {
    return renv_cmaq_sh;
  }

  public void setRenv_cmaq_sh(String renv_cmaq_sh) {
    this.renv_cmaq_sh = renv_cmaq_sh;
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

  public String getMeic_cache_conf_vm() {
    return meic_cache_conf_vm;
  }

  public void setMeic_cache_conf_vm(String meic_cache_conf_vm) {
    this.meic_cache_conf_vm = meic_cache_conf_vm;
  }

  public String getMeic_server_conf_vm() {
    return meic_server_conf_vm;
  }

  public void setMeic_server_conf_vm(String meic_server_conf_vm) {
    this.meic_server_conf_vm = meic_server_conf_vm;
  }

  public String getMeic_jar_conf() {
    return meic_jar_conf;
  }

  public void setMeic_jar_conf(String meic_jar_conf) {
    this.meic_jar_conf = meic_jar_conf;
  }

}
