package com.makenv.model.mc.meic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.makenv.model.mc.meic.config.MeicParams;
import com.makenv.model.mc.meic.request.MeicGetStatusRequest;
import com.makenv.model.mc.meic.request.MeicRunRequest;
import com.makenv.model.mc.meic.response.MeicGetStatusEnum;
import com.makenv.model.mc.meic.response.MeicGetStatusResponse;
import com.makenv.model.mc.meic.response.MeicRunResponse;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicUrlTools {

    private Logger logger = LoggerFactory.getLogger(MeicUrlTools.class);

    private String configInputPath;

    private MeicParams meicParams;

    public MeicUrlTools(String configPath) {

        this.configInputPath = configPath;

        init();
    }

    private void init() {

        Config config = ConfigFactory.parseFile(new File(configInputPath));

        this.meicParams = ConfigBeanFactory.create(config,MeicParams.class);

    }

    public void doMeicJob() {

        List<String> taskList = doMeicRunRequest();

        doMeicGetStateRequest(taskList);

    }

    private void doMeicGetStateRequest(List<String> taskList) {

        int count = 0;

        while(true) {

            if(count != 0 ) {

                try {
                    TimeUnit.SECONDS.sleep(2);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }

            if(taskList.size() <= 0) {

                 logger.info("check is over");

                 break;
            }

            Iterator<String> iterator = taskList.iterator();

            while(iterator.hasNext()) {

                String taskId = iterator.next();

                MeicGetStatusRequest meicGetStatusRequest = new MeicGetStatusRequest();

                StringBuffer stringBuffer = new StringBuffer("id");

                stringBuffer.append("=").append(taskId);

                try {

                    MeicGetStatusResponse meicGetStatusResponse = meicGetStatusRequest.doGetReuest(stringBuffer.toString(),meicParams.getMeicGetStatusUrl());

                    if(checkStatus(meicGetStatusResponse)){

                        iterator.remove();
                    };


                } catch (Exception e) {

                    e.printStackTrace();
                }

                count++;

            }

        }
    }

    private boolean checkStatus(MeicGetStatusResponse meicGetStatusResponse) {

        MeicGetStatusEnum[] meicGetStatusEnums = MeicGetStatusEnum.values();

        for(MeicGetStatusEnum meicGetStatusEnum:meicGetStatusEnums) {

            if(meicGetStatusEnum.getStatus().equalsIgnoreCase(meicGetStatusResponse.getStatus())) {

                return true;
            }


        }

        return false;

    }

    private List<String> doMeicRunRequest() {

        doCheckFile(meicParams);

        return doRunningRequest(meicParams);

    }

    private List<String> doRunningRequest(MeicParams meicParams) {

        List<String> taskList = new ArrayList<>();

        int dom = meicParams.getMax_dom();

        boolean flag = true;

        for(int i = 1;i <= dom;i++) {

            String meicTemplateDir = meicParams.getMeicConfTemplateDir();

            String filePath = String.format(MeicConstant.meicConfFile,dom);

            String meicTemplatePath = String.join("/",meicTemplateDir,filePath);

            File exsitsFile = new File(meicTemplatePath);

            MeicRunRequest meicRunRequest = new MeicRunRequest(meicParams.getMeicRunRequestUrl(),exsitsFile);

            MeicRunResponse meicRunResponse = meicRunRequest.doPost();

            if(meicRunResponse != null) {

                taskList.add(meicRunResponse.getData());
            }
            else {

                logger.info("please check your meic currDom" + dom);

                flag = false;
                //程序退出
                //System.exit(1);
            }

        }

        if(!flag) {

            //程序退出
            System.exit(1);
        }

        return taskList;

    }

    private void doCheckFile(MeicParams meicParams) {

        int dom = meicParams.getMax_dom();

        for(int i = 1;i < dom;i++) {

            String meicTemplateDir = meicParams.getMeicConfTemplateDir();

            String filePath = String.format(MeicConstant.meicConfFile,dom);

            String meicTemplatePath = String.join("/",meicTemplateDir,filePath);

            File exsitsFile = new File(meicTemplatePath);

            if(!exsitsFile.exists()) {

                logger.info("please check your meic conf");

                System.exit(1);
            }

        }
    }
}
