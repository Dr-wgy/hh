package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wgy on 2017/3/8.
 */
//执行geogrid和oceanFile
@Component
public class CreateDomainHelper{

    @Autowired
    private McConfigManager mcConfigManager;

    public boolean executeShell(DomainCreateBean domainCreateBean) {

        String renvPathName = buildRenv(domainCreateBean);

        String invokeFile = prepareExecShell(domainCreateBean,renvPathName);

        try {

            Runtime.getRuntime().exec("qsub  "+ invokeFile);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return false;
    }

    private String buildRenv(DomainCreateBean domainCreateBean){

        String renvDomainsh =  mcConfigManager.getSystemConfig().getTemplate().getRenv_domain_sh();

        String geogridTemplatePath = FilePathUtil.joinByDelimiter(mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid()
                .getCommon().getTemplate().getDirPath(), Constant.NAMELIST_WPS_GEOGRID_TEMPLATE);

        geogridTemplatePath = replaceRegex(geogridTemplatePath,domainCreateBean);
        GeoRenv geoRenv = new GeoRenv();
        geoRenv.setWrf_build_path(mcConfigManager.getSystemConfig().getRoot().getWrf());
        geoRenv.setScripts_path(mcConfigManager.getSystemConfig().getRoot().getScript());
        geoRenv.setNamelist_wps_geogrid_template(geogridTemplatePath);
        geoRenv.setGeog_data_path(mcConfigManager.getSystemConfig().getWorkspace().getShare().getInput().getGeog().getDirPath());

        String geogridDataPath = mcConfigManager.getSystemConfig()
                .getWorkspace().getUserid().
                getDomainid().getCommon().
                getData().getGeogrid().getDirPath();

        geogridDataPath = replaceRegex(geogridDataPath,domainCreateBean);
        //创建文件夹
        FileUtil.createFolder(geogridDataPath);
        geoRenv.setGeogrid_data_path(geogridDataPath);
        HashMap hashMap = new HashMap();
        hashMap.put("georenv",geoRenv);
        String content = VelocityUtil.buildTemplate(renvDomainsh,hashMap);

        String geogridRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getGeogrid();
        geogridRunPath = replaceRegex(geogridRunPath,domainCreateBean);

        String renvPathName = FilePathUtil.joinByDelimiter(geogridRunPath,Constant.DOMAIN_RENV_FILE);

        FileUtil.save(renvPathName,content);

        return renvPathName;


    }

    //准备执行的脚本
    private String prepareExecShell(DomainCreateBean domainCreateBean,String renvPathName){

        String moduleDomainCsh = mcConfigManager.getSystemConfig().getCsh().getModule_domain_csh();

        String script_path = mcConfigManager.getSystemConfig().getRoot().getScript();

        String geogridRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getGeogrid();

        geogridRunPath = replaceRegex(geogridRunPath,domainCreateBean);

        StringBuffer stringBuffer = new StringBuffer("#!/usr/bin/csh -f");

                    stringBuffer.append("\n")
                    .append("cd ")
                    .append(geogridRunPath)
                    .append("\n")
                    .append(FilePathUtil.joinByDelimiter(script_path,Constant.SYS_RENV_CSH))
                    .append("\n")
                    .append(moduleDomainCsh).append(" ")
                    .append(renvPathName);

        String invokeFile = FilePathUtil.joinByDelimiter(geogridRunPath,Constant.GEOGRID_SCRIPT_FILE);

        File file = new File(invokeFile);

        try {

            FileUtil.writeLocalFile(file, stringBuffer.toString());

        } catch (IOException e) {

            e.printStackTrace();
        }

        file.setExecutable(true);

        return invokeFile;

    }

    private String replaceRegex(String path,DomainCreateBean domainCreateBean) {

        return path.replaceAll("\\{userid\\}",domainCreateBean.getUserid()).replaceAll("\\{domainid\\}",domainCreateBean.getDomainid());
    }
}
