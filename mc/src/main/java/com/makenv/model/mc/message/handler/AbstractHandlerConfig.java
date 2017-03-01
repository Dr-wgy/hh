package com.makenv.model.mc.message.handler;

import com.makenv.model.mc.core.config.McConfigManager;

/**
 * Created by wgy on 2017/3/1.
 */
public abstract class AbstractHandlerConfig {

    protected McConfigManager mcConfigManager;

    public McConfigManager getMcConfigManager() {
        return this.mcConfigManager;
    }

    public void setMcConfigManager(McConfigManager mcConfigManager) {
        this.mcConfigManager = mcConfigManager;
    }
}
