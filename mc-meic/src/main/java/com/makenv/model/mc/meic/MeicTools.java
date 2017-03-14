package com.makenv.model.mc.meic;

import com.makenv.model.mc.core.bean.MeicParams;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

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

        //conf格式

        //Config config = ConfigFactory.parseFile(new File(configInputPath));

        //this.meicParams = ConfigBeanFactory.create(config,MeicParams.class);

        //json格式

        try {

            String content = FileUtil.readLocalFile(new File(configInputPath));

            this.meicParams = JacksonUtil.jsonToObj(content,MeicParams.class);

        } catch (IOException e) {

            logger.info("file is not correct");

            e.printStackTrace();
        }


    }

    public void doMeicJob() {

        String meicType = meicParams.getMeicType();

        IMeicTask meicTask = MeicFactory.getMeciTask(meicType,meicParams);

        //处理meicTask
        meicTask.doMeicJobs();

    }


}
