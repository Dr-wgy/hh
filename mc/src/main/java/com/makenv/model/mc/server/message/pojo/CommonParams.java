package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class CommonParams {

    private String map_proj;//地图投影方式#不同投影下参数不同

    private double ref_lat;//中心纬度

    private double ref_lon;//中心经度

    private double stand_lat1;//第1标准纬度

    private double stand_lat2;//第2标准纬度

    private double stand_lon;//标准经度

    private int  max_dom;//最大嵌套网格数

    private String dx;//x向分辨率（单位m）

    private String dy;//y向分辨率（单位m）

    public String getMap_proj() {
        return map_proj;
    }

    public void setMap_proj(String map_proj) {
        this.map_proj = map_proj;
    }

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

    public int getMax_dom() {
        return max_dom;
    }

    public void setMax_dom(int max_dom) {
        this.max_dom = max_dom;
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


}
