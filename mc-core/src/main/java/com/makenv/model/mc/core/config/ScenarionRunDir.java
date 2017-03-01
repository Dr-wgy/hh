package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionRunDir {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public ScenarionRunCctm getCctm() {
        return cctm;
    }

    public void setCctm(ScenarionRunCctm cctm) {
        this.cctm = cctm;
    }

    public ScenarionRunCctmPre getCctmpre() {
        return cctmpre;
    }

    public void setCctmpre(ScenarionRunCctmPre cctmpre) {
        this.cctmpre = cctmpre;
    }

    public ScenarionRundp getDp() {
        return dp;
    }

    public void setDp(ScenarionRundp dp) {
        this.dp = dp;
    }

    public ScenarionRunvideo getVideo() {
        return video;
    }

    public void setVideo(ScenarionRunvideo video) {
        this.video = video;
    }

    private String dirPath;

    private ScenarionRunCctm cctm;

    private ScenarionRunCctmPre cctmpre;

    private ScenarionRundp dp;

    private ScenarionRunvideo video;

    public static class ScenarionRunCctm{

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }
    }

    public static class ScenarionRunCctmPre{

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

    }

    public static class ScenarionRundp{

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

    }
    public static class ScenarionRunvideo{

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }
    }

}
