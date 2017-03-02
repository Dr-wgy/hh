package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.util.StringUtil;

/**
 * Created by wgy on 2017/3/2.
 */
public class Wrf {

    private int max_dom;

    private String e_we;

    private String e_sn;

    private int nlevel;

    private String dx;

    private String dy;

    private String parent_id;

    private String i_parent_start;

    private String j_parent_start;

    private String parent_grid_ratio;

    private String parent_time_step_ratio;

    private String eta_levels;

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

    public int getNlevel() {

        if(nlevel != 0) {

            String eta_levels = this.getEta_levels();

            if(!StringUtil.isEmpty(eta_levels)) {

                nlevel = eta_levels.split(",").length;
            }
        }

        return nlevel;
    }

    public void setNlevel(int nlevel) {
        this.nlevel = nlevel;
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
