package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class WrfParams {

  private String version;
  private String parent_id;

  private String parent_grid_ratio;

  private String parent_time_step_ratio;

  private String i_parent_start;

  private String j_parent_start;

  private String e_we;

  private String e_sn;

  private int e_vert;

  private String eta_levels;

  private String mp_physics;

  private String ra_lw_physics;

  private String ra_sw_physics;

  private String radt;

  private String sf_sfclay_physics;

  private String sf_surface_physics;

  private String bl_pbl_physics;

  private String bldt;

  private String cu_physics;

  private String cudt;

  private String pxlsm_smois_init;

  private String grid_fdda;

  private String gfdda_interval_m;

  private String grid_sfdda;

  private String sgfdda_interval_m;

  private String obs_nudge_opt;

  private String diff_opt;

  private String km_opt;

  private String damp_opt;

  private String dampcoef;

  private String khdif;

  private String kvdif;

  private String surface_input_source;

  private String sst_update;

  private String gfdda_end_h;

  private String sgfdda_end_h;

  private String hypsometric_opt;

  private String iswater;

  private String islake;

  private String isice;

  private String isurban;

  private String isoilwater;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getMp_physics() {
    return mp_physics;
  }

  public void setMp_physics(String mp_physics) {
    this.mp_physics = mp_physics;
  }

  public String getRa_lw_physics() {
    return ra_lw_physics;
  }

  public void setRa_lw_physics(String ra_lw_physics) {
    this.ra_lw_physics = ra_lw_physics;
  }

  public String getRa_sw_physics() {
    return ra_sw_physics;
  }

  public void setRa_sw_physics(String ra_sw_physics) {
    this.ra_sw_physics = ra_sw_physics;
  }

  public String getRadt() {
    return radt;
  }

  public void setRadt(String radt) {
    this.radt = radt;
  }

  public String getSf_sfclay_physics() {
    return sf_sfclay_physics;
  }

  public void setSf_sfclay_physics(String sf_sfclay_physics) {
    this.sf_sfclay_physics = sf_sfclay_physics;
  }

  public String getSf_surface_physics() {
    return sf_surface_physics;
  }

  public void setSf_surface_physics(String sf_surface_physics) {
    this.sf_surface_physics = sf_surface_physics;
  }

  public String getBl_pbl_physics() {
    return bl_pbl_physics;
  }

  public void setBl_pbl_physics(String bl_pbl_physics) {
    this.bl_pbl_physics = bl_pbl_physics;
  }

  public String getBldt() {
    return bldt;
  }

  public void setBldt(String bldt) {
    this.bldt = bldt;
  }

  public String getCu_physics() {
    return cu_physics;
  }

  public void setCu_physics(String cu_physics) {
    this.cu_physics = cu_physics;
  }

  public String getCudt() {
    return cudt;
  }

  public void setCudt(String cudt) {
    this.cudt = cudt;
  }

  public String getParent_id() {
    return parent_id;
  }

  public void setParent_id(String parent_id) {
    this.parent_id = parent_id;
  }

  public String getParent_grid_ratio() {
    return parent_grid_ratio;
  }

  public void setParent_grid_ratio(String parent_grid_ratio) {
    this.parent_grid_ratio = parent_grid_ratio;
  }

  public String getI_parent_start() {
    return i_parent_start;
  }

  public void setI_parent_start(String i_parent_start) {
    this.i_parent_start = i_parent_start;
  }

  public String getJ_parent_start() {
    return j_parent_start;
  }

  public void setJ_parent_start(String j_parent_start) {
    this.j_parent_start = j_parent_start;
  }

  public String getE_we() {
    return e_we;
  }

  public void setE_we(String e_we) {
    this.e_we = e_we;
  }

  public String getE_sn() {
    return e_sn;
  }

  public void setE_sn(String e_sn) {
    this.e_sn = e_sn;
  }

  public String getEta_levels() {
    return eta_levels;
  }

  public void setEta_levels(String eta_levels) {
    this.eta_levels = eta_levels;
  }

  public String getParent_time_step_ratio() {
    return parent_time_step_ratio;
  }

  public void setParent_time_step_ratio(String parent_time_step_ratio) {
    this.parent_time_step_ratio = parent_time_step_ratio;
  }

  public int getE_vert() {
    return e_vert;
  }

  public void setE_vert(int e_vert) {
    this.e_vert = e_vert;
  }

  public String getPxlsm_smois_init() {
    return pxlsm_smois_init;
  }

  public void setPxlsm_smois_init(String pxlsm_smois_init) {
    this.pxlsm_smois_init = pxlsm_smois_init;
  }

  public String getGrid_fdda() {
    return grid_fdda;
  }

  public void setGrid_fdda(String grid_fdda) {
    this.grid_fdda = grid_fdda;
  }

  public String getGfdda_interval_m() {
    return gfdda_interval_m;
  }

  public void setGfdda_interval_m(String gfdda_interval_m) {
    this.gfdda_interval_m = gfdda_interval_m;
  }

  public String getGrid_sfdda() {
    return grid_sfdda;
  }

  public void setGrid_sfdda(String grid_sfdda) {
    this.grid_sfdda = grid_sfdda;
  }

  public String getSgfdda_interval_m() {
    return sgfdda_interval_m;
  }

  public void setSgfdda_interval_m(String sgfdda_interval_m) {
    this.sgfdda_interval_m = sgfdda_interval_m;
  }

  public String getObs_nudge_opt() {
    return obs_nudge_opt;
  }

  public void setObs_nudge_opt(String obs_nudge_opt) {
    this.obs_nudge_opt = obs_nudge_opt;
  }

  public String getDiff_opt() {
    return diff_opt;
  }

  public void setDiff_opt(String diff_opt) {
    this.diff_opt = diff_opt;
  }

  public String getKm_opt() {
    return km_opt;
  }

  public void setKm_opt(String km_opt) {
    this.km_opt = km_opt;
  }

  public String getDamp_opt() {
    return damp_opt;
  }

  public void setDamp_opt(String damp_opt) {
    this.damp_opt = damp_opt;
  }

  public String getDampcoef() {
    return dampcoef;
  }

  public void setDampcoef(String dampcoef) {
    this.dampcoef = dampcoef;
  }

  public String getKhdif() {
    return khdif;
  }

  public void setKhdif(String khdif) {
    this.khdif = khdif;
  }

  public String getKvdif() {
    return kvdif;
  }

  public void setKvdif(String kvdif) {
    this.kvdif = kvdif;
  }

  public String getSurface_input_source() {
    return surface_input_source;
  }

  public void setSurface_input_source(String surface_input_source) {
    this.surface_input_source = surface_input_source;
  }

  public String getSst_update() {
    return sst_update;
  }

  public void setSst_update(String sst_update) {
    this.sst_update = sst_update;
  }

  public String getGfdda_end_h() {
    return gfdda_end_h;
  }

  public void setGfdda_end_h(String gfdda_end_h) {
    this.gfdda_end_h = gfdda_end_h;
  }

  public String getSgfdda_end_h() {
    return sgfdda_end_h;
  }

  public void setSgfdda_end_h(String sgfdda_end_h) {
    this.sgfdda_end_h = sgfdda_end_h;
  }

  public String getHypsometric_opt() {
    return hypsometric_opt;
  }

  public void setHypsometric_opt(String hypsometric_opt) {
    this.hypsometric_opt = hypsometric_opt;
  }

  public String getIswater() {
    return iswater;
  }

  public void setIswater(String iswater) {
    this.iswater = iswater;
  }

  public String getIslake() {
    return islake;
  }

  public void setIslake(String islake) {
    this.islake = islake;
  }

  public String getIsice() {
    return isice;
  }

  public void setIsice(String isice) {
    this.isice = isice;
  }

  public String getIsurban() {
    return isurban;
  }

  public void setIsurban(String isurban) {
    this.isurban = isurban;
  }

  public String getIsoilwater() {
    return isoilwater;
  }

  public void setIsoilwater(String isoilwater) {
    this.isoilwater = isoilwater;
  }
}
