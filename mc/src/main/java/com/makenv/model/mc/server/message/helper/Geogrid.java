package com.makenv.model.mc.server.message.helper;

/**
 * Created by wgy on 2017/3/1.
 */
public class Geogrid {

    private int max_dom;

    private String parent_id;

    private String parent_grid_ratio;

    private String i_parent_start;

    private String j_parent_start;

    private String e_we;

    private String e_sn;

    private String dx;

    private String dy;

    private String map_proj;

    private double ref_lat;

    private double ref_lon;

    private double stand_lat1;

    private double stand_lat2;

    private double stand_lon;

    public double getRef_lat() {
        return ref_lat;
    }

    public void setRef_lat(double ref_lat) {
        this.ref_lat = ref_lat;
    }

    public double getRef_lon() {
        return ref_lon;
    }

    public void setRef_lon(double ref_lon) {
        this.ref_lon = ref_lon;
    }

    public double getStand_lat1() {
        return stand_lat1;
    }

    public void setStand_lat1(double stand_lat1) {
        this.stand_lat1 = stand_lat1;
    }

    public double getStand_lat2() {
        return stand_lat2;
    }

    public void setStand_lat2(double stand_lat2) {
        this.stand_lat2 = stand_lat2;
    }

    public double getStand_lon() {
        return stand_lon;
    }

    public void setStand_lon(double stand_lon) {
        this.stand_lon = stand_lon;
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

    public String getMap_proj() {
        return map_proj;
    }

    public void setMap_proj(String map_proj) {
        this.map_proj = map_proj;
    }

    public int getMax_dom() {
        return max_dom;
    }

    public void setMax_dom(int max_dom) {
        this.max_dom = max_dom;
    }

}
