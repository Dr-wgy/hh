package com.makenv.model.mc.cli.is;

import com.makenv.model.mc.cli.is.cmd.CommadManager;
import com.makenv.model.mc.cli.is.config.RuleConfig;
import com.makenv.model.mc.cli.is.type.MissionTypeExecutor;
import com.makenv.model.mc.cli.is.util.ClassloaderUtil;
import com.makenv.model.mc.core.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Stack;

/**
 * Created by wgy on 2017/3/22.
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        if(args.length < 0) {

            logger.error("the args can not be empty");

            System.exit(1);
        }

        //初始化配置cliConfig
        CommadManager commadManager = new CommadManager(args);

        commadManager.init();
    }
}
