package com.makenv.model.mc.message.handler.wrf;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/2/22.
 * 所有的脚本是预处理脚本 c shell 脚本
 */
public class WrfPreProcessHandler extends AbstractHandlerConfig implements Handler {

    public WrfPreProcessHandler(McConfigManager mcConfigManager) {

        this.mcConfigManager = mcConfigManager;
    }

    private void generate_renv_wrf_pre_csh() {

        WrfPreBean wrfPreBean = new WrfPreBean();

        String fileNamePath =  mcConfigManager.getSystemConfigPath().getTemplate().getRenv_wrfpre_csh();

        Map map = new HashMap();

        map.put("wrfPre",wrfPreBean);

        String content = VelocityUtil.buildTemplate(fileNamePath,map);
    }

    private void link_wrf_pre_csh() {


    }

    private void runShell() {


    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        try {

            //生成wrf预处理环境变量 c shell 脚本
            generate_renv_wrf_pre_csh();

            //链接wrf预处理脚本
            link_wrf_pre_csh();

            //执行预处理脚本
            runShell();



        } catch (Exception e) {

            e.printStackTrace();
        }

        handlerChain.doHandler(handlerChain);

    }
    
}
