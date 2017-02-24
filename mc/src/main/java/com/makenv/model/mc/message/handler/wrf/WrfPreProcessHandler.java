package com.makenv.model.mc.message.handler.wrf;

import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;

/**
 * Created by wgy on 2017/2/22.
 * 所有的脚本是预处理脚本池 c shell 脚本
 */
public class WrfPreProcessHandler implements Handler {


    private void generate_renv_wrf_pre_csh() {


    }

    private void link_wrf_pre_csh() {


    }

    private void runShell() {


    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        //生成wrf预处理环境变量 c shell 脚本
        generate_renv_wrf_pre_csh();

        //链接wrf预处理脚本
        link_wrf_pre_csh();

        //执行预处理脚本
        runShell();

        handlerChain.doHandler(handlerChain);

    }
    
}
