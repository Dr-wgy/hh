package com.makenv.model.mc.message.handler.wrf;

/**
 * Created by wgy on 2017/3/1.
 */
public class WrfBean {

    private String namelist_wrf_template;

    private String start_date;

    private String end_date;

    private String start_hour;

    private String wrf_output;

    private int debug;

    private String logFilePathNameDir;

    public String getNamelist_wrf_template() {
        return namelist_wrf_template;
    }

    public void setNamelist_wrf_template(String namelist_wrf_template) {
        this.namelist_wrf_template = namelist_wrf_template;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getWrf_output() {
        return wrf_output;
    }

    public void setWrf_output(String wrf_output) {
        this.wrf_output = wrf_output;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public String getLogFilePathNameDir() {
        return logFilePathNameDir;
    }

    public void setLogFilePathNameDir(String logFilePathNameDir) {
        this.logFilePathNameDir = logFilePathNameDir;
    }
}
