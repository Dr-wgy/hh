package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class UserDataDir {

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    private String dirPath;

    private UserGeogrid geogrid;

    private Globaldatasets globaldatasets;

    public Griddesc getGriddesc() {
        return griddesc;
    }

    public void setGriddesc(Griddesc griddesc) {
        this.griddesc = griddesc;
    }

    private Griddesc griddesc;

    private OceanFile ocean;

    public UserGeogrid getGeogrid() {
        return geogrid;
    }

    public void setGeogrid(UserGeogrid geogrid) {
        this.geogrid = geogrid;
    }

    public Globaldatasets getGlobaldatasets() {
        return globaldatasets;
    }

    public void setGlobaldatasets(Globaldatasets globaldatasets) {
        this.globaldatasets = globaldatasets;
    }

    public OceanFile getOcean() {
        return ocean;
    }

    public void setOcean(OceanFile ocean) {
        this.ocean = ocean;
    }

    public static class UserGeogrid{

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;
    }

    public static class Griddesc{

        private String dirPath;

        private String filePath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

    public static class OceanFile{

        private String dirPath;

        private String filePath;

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

    }


}
