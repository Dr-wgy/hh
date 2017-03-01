package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ShareDirPath {
    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public ShareInputPath getInput() {
        return input;
    }

    public void setInput(ShareInputPath input) {
        this.input = input;
    }

    public ShareRunPath getRun() {
        return run;
    }

    public void setRun(ShareRunPath run) {
        this.run = run;
    }

    private String dirPath;

    private ShareInputPath input;

    private ShareRunPath run;

}
