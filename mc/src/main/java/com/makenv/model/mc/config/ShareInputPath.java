package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ShareInputPath {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public ShareFnl getFnl() {
        return fnl;
    }

    public void setFnl(ShareFnl fnl) {
        this.fnl = fnl;
    }

    public ShareGfs getGfs() {
        return gfs;
    }

    public void setGfs(ShareGfs gfs) {
        this.gfs = gfs;
    }

    public ShareGeo getGeog() {
        return geog;
    }

    public void setGeog(ShareGeo geog) {
        this.geog = geog;
    }

    public ShareSst getSst() {
        return sst;
    }

    public void setSst(ShareSst sst) {
        this.sst = sst;
    }

    public ShareUngribFnl getUngrib_fnl() {
        return ungrib_fnl;
    }

    public void setUngrib_fnl(ShareUngribFnl ungrib_fnl) {
        this.ungrib_fnl = ungrib_fnl;
    }

    public ShareUngribGfs getUngrib_gfs() {
        return ungrib_gfs;
    }

    public void setUngrib_gfs(ShareUngribGfs ungrib_gfs) {
        this.ungrib_gfs = ungrib_gfs;
    }

    private String dirPath;

    private ShareFnl fnl;

    private ShareGfs gfs;

    private ShareGeo geog;

    private ShareSst sst;

    private ShareUngribFnl ungrib_fnl;

    private ShareUngribGfs ungrib_gfs;

    public static class ShareFnl{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class ShareGeo{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;


    }

    public static class ShareGfs{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class ShareSst{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class ShareUngribFnl{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class ShareUngribGfs{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }


}
