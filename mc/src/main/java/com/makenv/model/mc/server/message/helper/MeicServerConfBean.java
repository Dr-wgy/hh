package com.makenv.model.mc.server.message.helper;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicServerConfBean extends MeicConfBean {

    private String model;
    private String submodel;

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setSubmodel(String submodel) {
        this.submodel = submodel;
    }

    public String getSubmodel() {
        return submodel;
    }
}
