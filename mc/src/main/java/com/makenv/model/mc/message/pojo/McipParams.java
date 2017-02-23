package com.makenv.model.mc.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wgy on 2017/2/23.
 */
public class McipParams {

    public String getX0() {
        return x0;
    }

    public void setX0(String x0) {
        this.x0 = x0;
    }

    public String getY0() {
        return y0;
    }

    public void setY0(String y0) {
        this.y0 = y0;
    }

    @JsonProperty("X0")
    private String x0;

    @JsonProperty("Y0")
    private String y0;


}
