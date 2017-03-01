package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ShareRunPath {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public RunUngribFnl getUngrib_fnl() {
        return ungrib_fnl;
    }

    public void setUngrib_fnl(RunUngribFnl ungrib_fnl) {
        this.ungrib_fnl = ungrib_fnl;
    }

    public RunUngribGfs getUngrib_gfs() {
        return ungrib_gfs;
    }

    public void setUngrib_gfs(RunUngribGfs ungrib_gfs) {
        this.ungrib_gfs = ungrib_gfs;
    }

    private String dirPath;

    private RunUngribFnl ungrib_fnl;

    private RunUngribGfs ungrib_gfs;

    public static class RunUngribFnl{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }

    public static class RunUngribGfs{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

    }
}
