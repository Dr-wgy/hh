package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionDir {

    private String dirPath;

    private ScenarionDataDir data;

    private ScenarionRunDir run;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public ScenarionDataDir getData() {
        return data;
    }

    public void setData(ScenarionDataDir data) {
        this.data = data;
    }

    public ScenarionRunDir getRun() {
        return run;
    }

    public void setRun(ScenarionRunDir run) {
        this.run = run;
    }




}
