package com.makenv.model.mc.message.handler.wrf;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.TimeUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;
import com.makenv.model.mc.message.handler.MissionType;
import com.makenv.model.mc.message.handler.util.XshellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/2/22.
 */
public class WrfRunHandler extends AbstractHandlerConfig implements Handler,MissionType {

    private Logger logger = LoggerFactory.getLogger(WrfRunHandler.class);

    private String wrfPathdateRunPath;

    private  String runDateDir;

    private String renvFile;

    private WrfParams wrfParams;

    public void setWrfPreParams(WrfParams wrfParams) {

        this.wrfParams = wrfParams;
    }


    public WrfRunHandler(McConfigManager mcConfigManager) {

        this.mcConfigManager = mcConfigManager;

        init();
    }

    private void init() {

        wrfPathdateRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getWrf_pathdate();

        wrfPathdateRunPath = replaceRegex(wrfPathdateRunPath);

    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        //生成运行环境变量 c shell 脚本
        generate_renv_wrf_csh();

        //执行预处理脚本
        run_wrf_Shell();

        handlerChain.doHandler(handlerChain);
    }


    private void run_wrf_Shell() {

        String invokeFile = prepareExecScript();

        if(invokeFile != null) {

            String qsubCmd = mcConfigManager.getSystemConfig().getPbs().getQsub();

            int ppn = mcConfigManager.getSystemConfig().getPbs().getPpn();//the number of virtual processors per node

            String taskName = String.join(":",wrfParams.getUserid(),wrfParams.getDomainid(),wrfParams.getGlobaldatasets(),getType());

            String logPathName = FilePathUtil.joinByDelimiter(runDateDir,"wrf.log");

            double nodes = Math.ceil((double)wrfParams.getCores()/ppn);

            qsubCmd = String.format(qsubCmd,
                    String.valueOf((long)nodes),
                    mcConfigManager.getSystemConfig().getPbs().getPpn(),
                    taskName,
                    logPathName,
                    invokeFile

            );

            //命令运行
            XshellUtil.executeCmd(qsubCmd);
        }

    }

    private String  prepareExecScript(){

        String moduleWrfCsh = mcConfigManager.getSystemConfig().getCsh().getModule_wrf_csh();

        String sb = "#!/usr/bin/env bash\n" +
                "cd " +
                runDateDir +
                "\n" +
                moduleWrfCsh + " " + renvFile;

        String invokeFile = null;

        try {

            invokeFile = FilePathUtil.joinByDelimiter(runDateDir,Constant.WRF_SCRIPT_FILE);

            File file = new File(invokeFile);

            FileUtil.writeLocalFile(file, sb);

            file.setExecutable(true);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return invokeFile;
    }

    private void generate_renv_wrf_csh() {

        WrfBean wrfBean = new WrfBean();

        wrfBean.setStart_hour(mcConfigManager.getSystemConfig().getModel().getStart_hour());

        wrfBean.setNamelist_wps_metgrid_template(FilePathUtil.joinByDelimiter(wrfPathdateRunPath, Constant.NAMELIST_WPS_METGRID_TEMPLATE));

        wrfBean.setNamelist_wrf_template(FilePathUtil.joinByDelimiter(wrfPathdateRunPath, Constant.NAMELIST_WRF_TEMPLATE));

        BeanUtils.copyProperties(wrfBean,wrfParams);

        wrfBean.setGeogrid_output_path(replaceRegex(mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGeogrid().getDirPath()));

        wrfBean.setMetgrid_output_path(
                replaceRegex(mcConfigManager.getSystemConfig().
                        getWorkspace().getUserid().getDomainid().
                        getCommon().getData().getGlobaldatasets().
                        getMetgrid().getDirPath()));

        wrfBean.setReal_output_path(replaceRegex(mcConfigManager.getSystemConfig().
                getWorkspace().getUserid().getDomainid().
                getCommon().getData().getGlobaldatasets().getReal().getDirPath()));

        wrfBean.setWrf_output_path(replaceRegex(mcConfigManager.getSystemConfig().
                getWorkspace().getUserid().getDomainid().
                getCommon().getData().getGlobaldatasets().getWrf().getDirPath()));

        wrfBean.setScripts_path(mcConfigManager.getSystemConfig().getRoot().getScript());

        wrfBean.setWrf_build_path(mcConfigManager.getSystemConfig().getRoot().getWrf());

        wrfBean.setRestart_run(0);

        wrfBean.setDebug(1);

        Map map = new HashMap();

        map.put("wrfBean",wrfBean);


        String fileNameTemDir = String.format(replaceRegex(mcConfigManager.
                getSystemConfig().getWorkspace().getUserid().
                getDomainid().getCommon().getRun().getWrf_pathdate()),wrfParams.getPathdate());

        String runDateDir = FilePathUtil.joinByDelimiter(fileNameTemDir, TimeUtil.formatDate(wrfParams.getStart_date(),"yyyyMMdd"));

        String fileNamePath = FilePathUtil.joinByDelimiter(runDateDir,Constant.WRF_RENV_FILE);

        renvFile = fileNamePath;

        logger.info("renv wrf file in --> :"+fileNamePath);

        String content = VelocityUtil.buildTemplate(fileNamePath,map);

        FileUtil.save(fileNamePath,content);

    }



    private String replaceRegex(String path) {

        return path.replaceAll("\\{userid\\}",wrfParams.getUserid()).replaceAll("\\{domainid\\}",wrfParams.getDomainid())

                .replaceAll("\\{globaldatasets\\}",wrfParams.getGlobaldatasets());
    }

    @Override
    public String getType() {

        return "WRF";
    }
}
