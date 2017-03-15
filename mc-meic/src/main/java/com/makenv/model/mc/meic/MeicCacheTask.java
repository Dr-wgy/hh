package com.makenv.model.mc.meic;

import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.meic.config.MeicCacheParams;
import com.makenv.model.mc.meic.constants.MeicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by wgy on 2017/3/14.
 */
public class MeicCacheTask implements IMeicTask {

    private Logger logger = LoggerFactory.getLogger(MeicCacheTask.class);

    private static final String[] linuxShell = new String[]{"/bin/sh","-c"};

    private static final String execJarPath = "cd %s ; java -Xmx4G .measures cmaq-%2d " +
            "this/etc/eumeasures.conf %s > %s 2<&1";


    private MeicCacheParams meicCacheParams;

    public MeicCacheParams getMeicCacheParams() {
        return meicCacheParams;
    }

    public void setMeicCacheParams(MeicCacheParams meicCacheParams) {
        this.meicCacheParams = meicCacheParams;
    }

    public MeicCacheTask(MeicCacheParams meicCacheParams) {
        this.meicCacheParams = meicCacheParams;
    }

    public MeicCacheTask() {


    }

    @Override
    public void doMeicJobs() {

        //0.生成日期映射
        Map<String,String> dateMapping = generateDateMapping();

        //1.生成执行依赖模板
        generateConfFile();

        //2.执行.measure.jar
        execShell();

        //ln -sf 执行文件链接
        symbolicLinkFile(dateMapping);

    }

    private void symbolicLinkFile(Map<String,String> dateMapping) {

        String emissiondir = meicCacheParams.getEmissiondir();

        int maxDom = meicCacheParams.getMaxDom();

        Set<Map.Entry<String,String>> dateEntrySets = dateMapping.entrySet();

        for(Map.Entry<String,String> entry : dateEntrySets) {

            String keyStr = entry.getKey();

            String valueStr = entry.getValue();

            LocalDate localDate = LocalTimeUtil.parse(keyStr);

            //换成dayofYear
            String str = DateTimeFormatter.ofPattern("yyyyDDD").format(localDate);

            for(int currDom = 1 ;currDom <= maxDom; currDom ++) {

                String sourceFile = FilePathUtil.joinByDelimiter(emissiondir,String.format(MeicConstant.meicOutFile,currDom) + str);

                String targetFile = FilePathUtil.joinByDelimiter(emissiondir,String.format(MeicConstant.meicOutFile,currDom) + valueStr);

                FileUtil.symbolicLink(sourceFile,targetFile);

            }
        }
    }

    //生成日期映射
    private Map<String,String> generateDateMapping() {

        Map dateMapping = new HashMap();

        int runDays = meicCacheParams.getRunDays();

        int timeDiff = meicCacheParams.getTimeDiff();

        String start_date = meicCacheParams.getStartDate();

        LocalDate startDate = LocalTimeUtil.parse(start_date,"yyyyMMdd");

        LocalTime localTime = LocalTime.of(0,0,0);

        for(int currdate = 0; currdate < runDays; currdate++) {

            LocalDateTime localDateTime = LocalDateTime.of(startDate,localTime);

            localDateTime = localDateTime.minus(timeDiff,ChronoUnit.HOURS);

            String dateTime = LocalTimeUtil.format(localDateTime.toLocalDate());

            dateMapping.put(dateTime,LocalTimeUtil.format(startDate));

            if(currdate == 0) {

                meicCacheParams.setStartDate(dateTime);
            }

            if(currdate == runDays - 1) {

                meicCacheParams.setEndDate(dateTime);
            }

            startDate = startDate.plus(1,ChronoUnit.DAYS);
        }

        return dateMapping;
    }


    private void execShell() {

        //生成maxDom
        int maxDom = meicCacheParams.getMaxDom();

        for(int currDom = 1; currDom <= maxDom; currDom ++ ) {

            String runPath = meicCacheParams.getRunPath();

            String meicFileConf = String.format(MeicConstant.meicConfFile,currDom, MeicType.MEICTYPE_CACHE.getType());

            String javaCmd = String.format(execJarPath,
                    meicCacheParams.getMeasureJarDir(),
                    currDom,FilePathUtil.joinByDelimiter(runPath,meicFileConf));

            List list = new ArrayList<String>();

            list.addAll(Arrays.asList(linuxShell));

            list.add(javaCmd);

            try {

                Process process = Runtime.getRuntime().exec((String[])list.toArray(new String[0]));

            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    private void generateConfFile() {

        int maxDom = meicCacheParams.getMaxDom();

        for(int currDom = 1; currDom <= maxDom; currDom ++ ) {

            String meicTemplateFileConf = String.format(MeicConstant.meicConfTemplateFile, currDom, MeicType.MEICTYPE_CACHE.getType());

            String confTemplateDir = meicCacheParams.getConfTemplateDir();

            String runPath = meicCacheParams.getRunPath();

            String confFileTemplatePath = FilePathUtil.joinByDelimiter(confTemplateDir, meicTemplateFileConf);

            String content = VelocityUtil.buildTemplate(confFileTemplatePath, createParams(currDom));

            String meicFileConf = String.format(MeicConstant.meicConfFile, currDom, MeicType.MEICTYPE_CACHE.getType());

            String targetConfFilePath = FilePathUtil.joinByDelimiter(runPath, meicFileConf);

            FileUtil.save(targetConfFilePath, content);

        }

    }

    private Map createParams(int currDom){

        Map paramsMap = new HashMap();

        paramsMap.put("emissiondir",meicCacheParams.getEmissiondir());

        paramsMap.put("taskId",meicCacheParams.getTaskId());
        //TODO 输出参数outPath需要修改
        String outPath = FilePathUtil.joinByDelimiter(meicCacheParams.getEmissiondir(),String.format(MeicConstant.meicOutFile,currDom));

        paramsMap.put("outpath",outPath);

        paramsMap.put("start_date",meicCacheParams.getStartDate());

        paramsMap.put("end_date",meicCacheParams.getEndDate());

        paramsMap.put("pslist",meicCacheParams.getPslist());

        paramsMap.put("sslist",meicCacheParams.getSslist());

        paramsMap.put("meganpath",meicCacheParams.getMeganPathPrefix());

        paramsMap.put("shutdown",meicCacheParams.isMeganShutdown());

        return paramsMap;

    }

}
