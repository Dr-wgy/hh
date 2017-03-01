package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class Globaldatasets {

    public String getDirPath() {
        return dirPath;
    }

    public GlobalMcip getMcip() {
        return mcip;
    }

    public void setMcip(GlobalMcip mcip) {
        this.mcip = mcip;
    }

    public GlobalMegan getMegan() {
        return megan;
    }

    public void setMegan(GlobalMegan megan) {
        this.megan = megan;
    }

    public GlobalReal getReal() {
        return real;
    }

    public void setReal(GlobalReal real) {
        this.real = real;
    }

    public GlobalWrf getWrf() {
        return wrf;
    }

    public void setWrf(GlobalWrf wrf) {
        this.wrf = wrf;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    private String dirPath;

    private GlobalMcip mcip;

    private GlobalMegan megan;

    public GlobalMetgrid getMetgrid() {
        return metgrid;
    }

    public void setMetgrid(GlobalMetgrid metgrid) {
        this.metgrid = metgrid;
    }

    private GlobalMetgrid metgrid;

    private GlobalReal real;

    private GlobalWrf wrf;

    public GlobalWrfDp getWrfdp() {
        return wrfdp;
    }

    public void setWrfdp(GlobalWrfDp wrfdp) {
        this.wrfdp = wrfdp;
    }

    private GlobalWrfDp wrfdp;

    public static class GlobalMcip {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }

    public static class GlobalMegan {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }
    public static class GlobalReal {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;


    }

    public static class GlobalMetgrid {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }

}
