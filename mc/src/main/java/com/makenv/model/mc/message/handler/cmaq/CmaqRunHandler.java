package com.makenv.model.mc.message.handler.cmaq;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Domain;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;

/**
 * Created by wgy on 2017/2/22.
 */
public class CmaqRunHandler extends AbstractHandlerConfig implements Handler,Domain {

    public CmaqRunHandler(McConfigManager mcConfigManager) {
        this.mcConfigManager = mcConfigManager;
    }

    private int domain;

    public void setDomain(int domain) {
        this.domain = domain;
    }

    @Override
    public int getDomain() {

        return this.domain;
    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        handlerChain.doHandler(handlerChain);
    }
}
