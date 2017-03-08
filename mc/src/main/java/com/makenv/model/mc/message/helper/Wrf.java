package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.util.StringUtil;

/**
 * Created by wgy on 2017/3/2.
 */
public class Wrf {

    private int max_dom;

    private String e_we;

    private String e_sn;

    private int e_vert;

    private String dx;

    private String dy;

    private String parent_id;

    private String i_parent_start;

    private String j_parent_start;

    private String parent_grid_ratio;

    private String parent_time_step_ratio;

    private String eta_levels;

    private int do_fdda;//#是否开启fdda

    private int do_sfdda;//#是否开启sfdda

    private int do_obsgrid;//#是否开启obsgrid

    private int pxlsm_smois_init;//#是否从LANDUSE.TBL中读取初始土壤温度

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

    public int getDo_fdda() {
        return do_fdda;
    }

    public void setDo_fdda(int do_fdda) {
        this.do_fdda = do_fdda;
    }

    public int getDo_sfdda() {
        return do_sfdda;
    }

    public void setDo_sfdda(int do_sfdda) {
        this.do_sfdda = do_sfdda;
    }

    public int getDo_obsgrid() {
        return do_obsgrid;
    }

    public void setDo_obsgrid(int do_obsgrid) {
        this.do_obsgrid = do_obsgrid;
    }

    public int getPxlsm_smois_init() {
        return pxlsm_smois_init;
    }

    public void setPxlsm_smois_init(int pxlsm_smois_init) {
        this.pxlsm_smois_init = pxlsm_smois_init;
    }

    public int getMax_dom() {
        return max_dom;
    }

    public void setMax_dom(int max_dom) {
        this.max_dom = max_dom;
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

    public int getE_vert() {

        if(e_vert != 0) {

            String eta_levels = this.getEta_levels();

            if(!StringUtil.isEmpty(eta_levels)) {

                e_vert = eta_levels.split(",").length;
            }
        }

        return e_vert;
    }

    public void setE_vert(int e_vert) {
        this.e_vert = e_vert;
    }

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
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

    public String getParent_grid_ratio() {
        return parent_grid_ratio;
    }

    public void setParent_grid_ratio(String parent_grid_ratio) {
        this.parent_grid_ratio = parent_grid_ratio;
    }

    public String getParent_time_step_ratio() {
        return parent_time_step_ratio;
    }

    public void setParent_time_step_ratio(String parent_time_step_ratio) {
        this.parent_time_step_ratio = parent_time_step_ratio;
    }

    public String getEta_levels() {
        return eta_levels;
    }

    public void setEta_levels(String eta_levels) {
        this.eta_levels = eta_levels;
    }

}
