package com.makenv.model.mc.server.message.helper;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicConfBean {

    private int dx;
    private int dy;
    private int xcells;
    private int ycells;
    private double stand_lat1;
    private double ref_lat;
    private double ref_lon;
    private double stand_lat2;
    private int currdom;
    private int meicType;
    private String name;
    private Double xorig;
    private Double yorig;

    public Double getXorig() {
        return xorig;
    }

    public void setXorig(Double xorig) {
        this.xorig = xorig;
    }

    public Double getYorig() {
        return yorig;
    }

    public void setYorig(Double yorig) {
        this.yorig = yorig;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getXcells() {
        return xcells;
    }

    public void setXcells(int xcells) {
        this.xcells = xcells;
    }

    public int getYcells() {
        return ycells;
    }

    public void setYcells(int ycells) {
        this.ycells = ycells;
    }

    public double getStand_lat1() {
        return stand_lat1;
    }

    public void setStand_lat1(double stand_lat1) {
        this.stand_lat1 = stand_lat1;
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

    public double getStand_lat2() {
        return stand_lat2;
    }

    public void setStand_lat2(double stand_lat2) {
        this.stand_lat2 = stand_lat2;
    }

    public int getCurrdom() {
        return currdom;
    }

    public void setCurrdom(int currdom) {
        this.currdom = currdom;
    }

    public int getMeicType() {
        return meicType;
    }

    public void setMeicType(int meicType) {
        this.meicType = meicType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
