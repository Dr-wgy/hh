package com.makenv.model.mc.cli.is.config;

import com.makenv.model.mc.cli.is.Application;
import com.makenv.model.mc.cli.is.util.ClassloaderUtil;
import com.makenv.model.mc.cli.is.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wgy on 2017/3/22.
 */
public class RuleConfig {

    private Logger logger = LoggerFactory.getLogger(RuleConfig.class);

    public String getRuleExcelPath() {
        return ruleExcelPath;
    }

    private String ruleExcelPath;

    private final String cliProperties = "cli-is.properties";

    public RuleConfig() {

        init();

    }

    private void init() {

        try {

            InputStream input = Application.class.getClassLoader().getResourceAsStream(cliProperties);

            if(input == null) {

                input = new FileInputStream(new File(cliProperties));
            }

            Properties props = new Properties();

            props.load(input);

            this.ruleExcelPath = props.getProperty("ruleExcelPath");


        } catch (Exception e) {

            logger.error("",e);
            //非正常退出
            System.exit(1);
            //e.printStackTrace();
        }


    }
}
