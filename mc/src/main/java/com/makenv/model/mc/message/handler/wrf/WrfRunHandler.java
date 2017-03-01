package com.makenv.model.mc.message.handler.wrf;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;

/**
 * Created by wgy on 2017/2/22.
 */
public class WrfRunHandler extends AbstractHandlerConfig implements Handler {

    @Override
    public void doHandler(HandlerChain handlerChain) {

        //生成运行环境变量 c shell 脚本
        generate_renv_wrf_csh();

        //链接wrf预处理脚本
        link_wrf_csh();

        //执行预处理脚本
        run_wrf_Shell();

        handlerChain.doHandler(handlerChain);
    }

    public WrfRunHandler(McConfigManager mcConfigManager) {

        this.mcConfigManager = mcConfigManager;
    }

    private void run_wrf_Shell() {

    }

    private void link_wrf_csh() {

    }

    private void generate_renv_wrf_csh() {


    }
}
