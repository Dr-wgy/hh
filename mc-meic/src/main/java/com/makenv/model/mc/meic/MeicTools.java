package com.makenv.model.mc.meic;

import com.makenv.model.mc.core.bean.MeicParams;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicTools {

    private Logger logger = LoggerFactory.getLogger(MeicTools.class);

    private String configInputPath;

    private MeicParams meicParams;

    public MeicTools(String configPath) {

        this.configInputPath = configPath;

        init();
    }

    private void init() {

        Config config = ConfigFactory.parseFile(new File(configInputPath));

        this.meicParams = ConfigBeanFactory.create(config,MeicParams.class);

    }

    public void doMeicJob() {

        String meicType = meicParams.getMeicType();

        IMeicTask meicTask = MeicFactory.getMeciTask(meicType,meicParams);

        //处理meicTask
        meicTask.doMeicJobs();

    }


}
