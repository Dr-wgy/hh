package com.makenv.model.mc.meic;

import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.meic.config.MeicServerParams;
import com.makenv.model.mc.meic.constants.MeicType;
import com.makenv.model.mc.meic.constants.ResultStatus;
import com.makenv.model.mc.meic.request.MeicGetStatusRequest;
import com.makenv.model.mc.meic.request.MeicRunRequest;
import com.makenv.model.mc.meic.response.MeicGetStatusEnum;
import com.makenv.model.mc.meic.response.MeicGetStatusResponse;
import com.makenv.model.mc.meic.response.MeicRunResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wgy on 2017/3/14.
 */
public class MeicServerTask implements IMeicTask {

    private Logger logger = LoggerFactory.getLogger(MeicServerTask.class);

    public void setMeicServerParams(MeicServerParams meicServerParams) {
        this.meicServerParams = meicServerParams;
    }

    private MeicServerParams meicServerParams;

    public MeicServerTask() {
    }

    public MeicServerTask(MeicServerParams meicServerParams) {

        this.meicServerParams = meicServerParams;

    }

    @Override
    public void doMeicJobs() {

        // 0.生成日期映射
        Map<String,String> dateMapping = generateDateMapping();

        logger.info("genertate conf start");

        // 1.生成配置文件
        generateConfFile();

        logger.info("genertate conf end");

        // 2.执行接口生成meic
        List<String> taskList = doMeicRunRequest();

        logger.info("check meicTask is finish or not");

        //获取任务执行转态
        doMeicGetStateRequest(taskList);

        logger.info("execute symbolicLinkFile");
        // 3. ln -sf 执行文件链接
        symbolicLinkFile(dateMapping);
    }

    private int calculSleepMinute(int size) {

        return MeicConstant.RUN_MINIUTE;
    }

    private void generateConfFile() {

        int maxDom = meicServerParams.getMaxDom();

        for(int currDom = 1; currDom <= maxDom; currDom ++ ) {

            String meicFileTemConf = String.format(MeicConstant.meicConfTemplateFile, currDom, MeicType.MEICTYPE_SERVER.getType());

            String confTemplateDir = meicServerParams.getConfTemplateDir();

            String runPath = meicServerParams.getRunPath();

            String confFileTemplatePath = FilePathUtil.joinByDelimiter(confTemplateDir, meicFileTemConf);

            logger.info(confFileTemplatePath);

            logger.info(String.valueOf(new File(confFileTemplatePath).exists()));

            String content = VelocityUtil.buildTemplate(confFileTemplatePath, createServerParams(currDom));

            String meicFileConf = String.format(MeicConstant.meicConfFile, currDom, MeicType.MEICTYPE_SERVER.getType());

            String targetConfFilePath = FilePathUtil.joinByDelimiter(runPath, meicFileConf);

            FileUtil.save(targetConfFilePath, content);

        }
    }

    private Map<String, ?> createServerParams(int currdom) {

        Map paramsMap = new HashMap();

        paramsMap.put("emissiondir",meicServerParams.getEmissiondir());

        paramsMap.put("meic_city_path",meicServerParams.getMeicCityConfigPath());

        paramsMap.put("controlfile",meicServerParams.getControlfile());

        //TODO 输出参数outPath需要修改
        String outPath = FilePathUtil.joinByDelimiter(meicServerParams.getEmissiondir(),String.format(MeicConstant.meicOutFile,currdom));

        paramsMap.put("outpath",outPath);

        paramsMap.put("start_date",meicServerParams.getStartDate());

        paramsMap.put("end_date",meicServerParams.getEndDate());

        paramsMap.put("pslist",meicServerParams.getPslist());

        paramsMap.put("onefilehours",meicServerParams.getOnefilehours());

        paramsMap.put("firsthour",meicServerParams.getFirsthour());

        paramsMap.put("sslist",meicServerParams.getSslist());

        paramsMap.put("meganpath",meicServerParams.getMeganPathPrefix());

        paramsMap.put("shutdown",meicServerParams.isMeganShutdown());

        paramsMap.put("username",meicServerParams.getUsername());

        paramsMap.put("password",meicServerParams.getPassword());

        return paramsMap;
    }

    private void symbolicLinkFile(Map<String, String> dateMapping) {

        String emissiondir = meicServerParams.getEmissiondir();

        int maxDom = meicServerParams.getMaxDom();

        Set<Map.Entry<String,String>> dateEntrySets = dateMapping.entrySet();

        for(Map.Entry<String,String> entry : dateEntrySets) {

            String keyStr = entry.getKey();

            String valueStr = entry.getValue();

            LocalDate localDate = LocalTimeUtil.parse(keyStr);

            LocalDate valueDate = LocalTimeUtil.parse(valueStr);

            //换成dayofYear
            String str = DateTimeFormatter.ofPattern("yyyyDDD").format(localDate);

            for(int currDom = 1 ;currDom <= maxDom; currDom ++) {

                String sourceFile = FilePathUtil.joinByDelimiter(emissiondir,String.format(MeicConstant.meicOutFile,currDom) + str);

                String targetFile = FilePathUtil.joinByDelimiter(emissiondir,String.format(MeicConstant.meicOutFile,currDom) + LocalTimeUtil.format(valueDate,"yyyyMMdd"));

                FileUtil.symbolicLink(sourceFile,targetFile);

            }
        }

    }

    private Map<String, String> generateDateMapping() {

        Map dateMapping = new HashMap();

        int runDays = meicServerParams.getRunDays();

        int timeDiff = meicServerParams.getTimeDiff();

        String start_date = meicServerParams.getStartDate();

        LocalDate startDate = LocalTimeUtil.parse(start_date,"yyyyMMdd");

        LocalTime localTime = LocalTime.of(0,0,0);

        for(int currdate = 0; currdate < runDays; currdate++) {

            LocalDateTime localDateTime = LocalDateTime.of(startDate,localTime);

            localDateTime = localDateTime.minus(timeDiff, ChronoUnit.HOURS);

            String dateTime = LocalTimeUtil.format(localDateTime.toLocalDate());

            dateMapping.put(dateTime,LocalTimeUtil.format(startDate));

            if(currdate == 0) {

                meicServerParams.setStartDate(dateTime);
            }

            if(currdate == runDays - 1) {

                meicServerParams.setEndDate(dateTime);
            }

            startDate = startDate.plus(1,ChronoUnit.DAYS);
        }

        return dateMapping;
    }

    private void doMeicGetStateRequest(List<String> taskList) {

        int count = 0;

        while(true) {

            if(count != 0 ) {

                try {
                    TimeUnit.SECONDS.sleep(meicServerParams.getSleepSeconds());

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

                    MeicGetStatusResponse meicGetStatusResponse = meicGetStatusRequest.doGetReuest(stringBuffer.toString(),meicServerParams.getMeicGetStatusUrl());


                    if(checkSuccessStatus(meicGetStatusResponse)){

                        iterator.remove();
                    };

                    if(checkFaildStatus(meicGetStatusResponse)) {

                        logger.info("please check your meic-tools log");
                        //meicTool 运行失败
                        System.exit(1);
                    }


                } catch (Exception e) {

                    //e.printStackTrace();

                    logger.info("request is failed please check your url: "+meicServerParams.getMeicRunRequestUrl(),e);
                }

                count++;

            }

        }
    }

    private boolean checkSuccessStatus(MeicGetStatusResponse meicGetStatusResponse) {

        return MeicGetStatusEnum.DONE_STATUS.getStatus().equalsIgnoreCase(meicGetStatusResponse.getData());

    }

    private boolean checkFaildStatus(MeicGetStatusResponse meicGetStatusResponse) {

        return MeicGetStatusEnum.FAIL_STATUS.getStatus().equalsIgnoreCase(meicGetStatusResponse.getData()) ||
                MeicGetStatusEnum.KILL_STATUS.getStatus().equalsIgnoreCase(meicGetStatusResponse.getData());

    }

    private List<String> doMeicRunRequest() {

        doCheckFile();

        return doRunningRequest();

    }

    private List<String> doRunningRequest() {

        List<String> taskList = new ArrayList<>();

        int maxDom = meicServerParams.getMaxDom();

        boolean flag = true;

        for(int currDom = 1;currDom <= maxDom;currDom ++ ) {

            String meicConfDir = meicServerParams.getRunPath();

            String filePath = String.format(MeicConstant.meicConfFile,currDom,MeicType.MEICTYPE_SERVER.getType());

            String meicTemplatePath = String.join("/",meicConfDir,filePath);

            File exsitsFile = new File(meicTemplatePath);

            MeicRunRequest meicRunRequest = new MeicRunRequest(meicServerParams.getMeicRunRequestUrl(),exsitsFile);

            MeicRunResponse meicRunResponse = meicRunRequest.doPost();

            if(meicRunResponse != null && ResultStatus.REQUEST_SUCCESS.getStatus().equals(meicRunResponse.getStatus())) {

                taskList.add(meicRunResponse.getData());
            }
            else {

                logger.info("please check your meic currDom" + maxDom);

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

    private void doCheckFile() {

        int dom = meicServerParams.getMaxDom();

        for(int i = 1;i < dom;i++) {

            String meicTemplateDir = meicServerParams.getConfTemplateDir();

            String filePath = String.format(MeicConstant.meicConfTemplateFile,dom,MeicType.MEICTYPE_SERVER.getType());

            String meicTemplatePath = String.join("/",meicTemplateDir,filePath);

            File exsitsFile = new File(meicTemplatePath);

            if(!exsitsFile.exists()) {

                logger.info("please check your meic conf");

                System.exit(1);
            }

        }
    }
}
