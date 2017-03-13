package com.makenv.model.mc.meic.config;

import java.util.List;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicParams {

    private String meicRunRequestUrl;

    private String meicGetStatusUrl;

    private String meicConfTemplateDir;

    private int max_dom;


    public String getMeicRunRequestUrl() {
        return meicRunRequestUrl;
    }

    public void setMeicRunRequestUrl(String meicRunRequestUrl) {
        this.meicRunRequestUrl = meicRunRequestUrl;
    }

    public String getMeicGetStatusUrl() {
        return meicGetStatusUrl;
    }

    public void setMeicGetStatusUrl(String meicGetStatusUrl) {
        this.meicGetStatusUrl = meicGetStatusUrl;
    }

    public String getMeicConfTemplateDir() {
        return meicConfTemplateDir;
    }

    public void setMeicConfTemplateDir(String meicConfTemplateDir) {
        this.meicConfTemplateDir = meicConfTemplateDir;
    }

    public int getMax_dom() {
        return max_dom;
    }

    public void setMax_dom(int max_dom) {
        this.max_dom = max_dom;
    }
}
