package com.makenv.model.mc.server.message.util;

import java.io.InputStream;

/**
 * Created by wgy on 2017/3/17.
 */
public class ShellResult {

    private boolean executeFlag;

    private String output;

    private String errorput;

    private InputStream errorStream;

    private InputStream infoStream;

    public boolean isExecuteFlag() {
        return executeFlag;
    }

    public void setExecuteFlag(boolean executeFlag) {
        this.executeFlag = executeFlag;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public InputStream getErrorStream() {
        return errorStream;
    }

    public void setErrorStream(InputStream errorStream) {
        this.errorStream = errorStream;
    }

    public InputStream getInfoStream() {
        return infoStream;
    }

    public void setInfoStream(InputStream infoStream) {
        this.infoStream = infoStream;
    }

    public String getErrorput() {
        return errorput;
    }

    public void setErrorput(String errorput) {
        this.errorput = errorput;
    }


}
