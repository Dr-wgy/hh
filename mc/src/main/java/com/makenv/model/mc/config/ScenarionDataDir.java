package com.makenv.model.mc.config;

import com.makenv.model.mc.enumeration.ScenarioType;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionDataDir {

    public CctmDir getCctm() {
        return cctm;
    }

    public void setCctm(CctmDir cctm) {
        this.cctm = cctm;
    }

    public ScenarionDpDir getDp() {
        return dp;
    }

    public void setDp(ScenarionDpDir dp) {
        this.dp = dp;
    }

    public ScenarioDataIcon getIcon() {
        return icon;
    }

    public void setIcon(ScenarioDataIcon icon) {
        this.icon = icon;
    }

    public ScenarioDataVideo getVideo() {
        return video;
    }

    public void setVideo(ScenarioDataVideo video) {
        this.video = video;
    }

    public String getDirPath() {
        return dirPath;

    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public BconDir getBcon() {
        return bcon;
    }

    public void setBcon(BconDir bcon) {
        this.bcon = bcon;
    }

    private String dirPath;

    private BconDir bcon;

    private CctmDir cctm;

    private ScenarionDpDir dp;

    public static class BconDir{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class CctmDir {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    private ScenarioDataIcon icon;

    private ScenarioDataVideo video;

    public static class ScenarioDataIcon {

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }
    }

    public static class ScenarioDataVideo {

        private String dirPath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }
    }
}
