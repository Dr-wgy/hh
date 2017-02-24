package com.makenv.model.mc.velocity;

/**
 * Created by wgy on 2017/2/24.
 */
/* COORD-NAME
   COORDTYPE, P_ALP, P_BET, P_GAM, XCENT, YCENT*/

/* GRID-NAME
   COORD-NAME, XORIG, YORIG, XCELL, YCELL, NCOLS, NROWS, NTHIK*/
public class GriddescBean {

    private String coorName;

    private String map_proj; //投影方式

    private int coordType;

    private double stand_lat1;//P_ALP

    private double stand_lat2;//P_BET

    private double stand_lon;//P_GAM

    private double ref_lon;//XCENT

    private double ref_lat;//YCENT

    private String gridName;

    private double xorig;

    private double yorig;

    private double dx;//XCELL

    private double dy;//YCELL

    private int nx;//NCOLS

    private int ny;//NROWS

    private int nthik = 1;//Boundary perimeter thickness

    public String getCoorName() {
        return coorName;
    }

    public void setCoorName(String coorName) {
        this.coorName = coorName;
    }

    public int getCoordType() {

        if("lambert".equalsIgnoreCase(map_proj)) {

            coordType = 2;
        }

        return coordType;
    }

    public void setCoordType(int coordType) {
        this.coordType = coordType;
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

    public double getRef_lon() {
        return ref_lon;
    }

    public void setRef_lon(double ref_lon) {
        this.ref_lon = ref_lon;
    }

    public double getRef_lat() {
        return ref_lat;
    }

    public void setRef_lat(double ref_lat) {
        this.ref_lat = ref_lat;
    }

    public String getGridName() {

        String gridNameTemPlate = "%s_%d%%s%d%s";

        if("lambert".equals(map_proj)) {
            String lat;
            String lon;
            if(ref_lat > 0) lat = "N";
            else lat = "S";
            if(ref_lon > 0) lon = "E";
            else lon = "W";
            gridName = String.format(gridNameTemPlate,"LAM",(int)ref_lat,lat,(int) ref_lon,lon);
        }

        else {

            throw  new RuntimeException("参数异常请检查");
        }


        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public double getXorig() {
        return xorig;
    }

    public void setXorig(double xorig) {
        this.xorig = xorig;
    }

    public double getYorig() {
        return yorig;
    }

    public void setYorig(double yorig) {
        this.yorig = yorig;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public int getNthik() {
        return nthik;
    }

    public void setNthik(int nthik) {
        this.nthik = nthik;
    }

    public String getMap_proj() {
        return map_proj;
    }

    public void setMap_proj(String map_proj) {
        this.map_proj = map_proj;
    }
}
