package com.makenv.model.mc.cli.is.config;

import com.makenv.model.mc.cli.is.Application;
import com.makenv.model.mc.cli.is.util.ClassloaderUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wgy on 2017/3/22.
 */
public class RuleConfig {

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

            System.out.println(ClassloaderUtil.getCurrentClassloaderDetail());

            InputStream input = Application.class.getClassLoader().getResourceAsStream(cliProperties);

            if(input == null) {

                input = new FileInputStream(new File(cliProperties));
            }

            Properties props = new Properties();

            props.load(input);

            this.ruleExcelPath = props.getProperty("ruleExcelPath");


        } catch (Exception e) {

            e.printStackTrace();
        }


    }
}
