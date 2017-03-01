package com.makenv.model.mc.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class UserCommonDir {

    private String dirPath;

    private UserDataDir data;

    public UserDataDir getData() {
        return data;
    }

    public void setData(UserDataDir data) {
        this.data = data;
    }

    public UserRunDir getRun() {
        return run;
    }

    public void setRun(UserRunDir run) {
        this.run = run;
    }

    private UserRunDir run;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }
}
