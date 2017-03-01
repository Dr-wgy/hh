package com.makenv.model.mc.message.handler.dp;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;

/**
 * Created by wgy on 2017/2/22.
 */
public class PostProcessHandler extends AbstractHandlerConfig implements Handler {


    public PostProcessHandler(McConfigManager mcConfigManager) {

        this.mcConfigManager = mcConfigManager;
    }


    @Override
    public void doHandler(HandlerChain handlerChain) {

        handlerChain.doHandler(handlerChain);

    }
}
