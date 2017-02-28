package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class GlobalWrf {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public Pathdate getPathdate() {
        return pathdate;
    }

    public void setPathdate(Pathdate pathdate) {
        this.pathdate = pathdate;
    }

    private String dirPath;

    private Pathdate pathdate;

    public static class Pathdate {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }
}
