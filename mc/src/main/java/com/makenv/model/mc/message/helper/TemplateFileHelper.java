package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.controller.ModelController;
import com.makenv.model.mc.message.pojo.CommonParams;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.TaskDomain;
import com.makenv.model.mc.message.pojo.WrfParams;
import com.makenv.model.mc.message.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/3/1.
 */
@Component
public class TemplateFileHelper {

    private Logger logger = LoggerFactory.getLogger(TemplateFileHelper.class);

    @Autowired
    private McConfigManager mcConfigManager;

    private String templateDir;

    @PostConstruct
    public void postContruct(){

      templateDir = mcConfigManager.getSystemConfigPath().getWorkspace().getUserid().getDomainid().getCommon().getTemplate().getDirPath();
    }


    public boolean generateNamelist(DomainCreateBean domainCreateBean){

        boolean ipwrfFlag = generateNamelistIpwrf(domainCreateBean);

        logger.info("genertateNamelistIpwrf is " + (ipwrfFlag ? "success":"failed"));

        boolean oaFlag = generateNamelistOa(domainCreateBean);

        logger.info("generateNamelistOa is " + (oaFlag ? "success":"failed"));

        boolean wpsGeogridFlag = generateNamelistwpsGeogrid(domainCreateBean);

        logger.info("generateNamelistwpsGeogrid is " + (wpsGeogridFlag ? "success":"failed"));

        boolean wrfFlag = generateNamelistwrf(domainCreateBean);

        logger.info("generateNamelistwrf is " + (wrfFlag ? "success":"failed"));

        boolean metgridFlag = generateNamelistwpsMetgrid(domainCreateBean);

        logger.info("generateNamelistwpsMetgrid is " + (metgridFlag ? "success":"failed"));

        return ipwrfFlag && oaFlag && wpsGeogridFlag && wrfFlag && metgridFlag;

    }

    private boolean generateNamelistIpwrf(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_ipxwrf_template();

        boolean flag = true;

        try {

            FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/",templateDir,"namelist.ipxwrf.template"));

        } catch (IOException e) {

            flag = false;

            e.printStackTrace();
        }

        return flag;

    }

    private boolean generateNamelistOa(DomainCreateBean domainCreateBean) {

        String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_oa_template();

        boolean flag = true;

        try {

            FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/",templateDir,"namelist.oa.template"));

        } catch (IOException e) {

            flag = false;

            e.printStackTrace();
        }

        return false;

    }

    private boolean generateNamelistwpsGeogrid(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_wps_geogrid_template();

        Map map = new HashMap<>();

        Geogrid geogrid = new Geogrid();

        CommonParams commonParams = domainCreateBean.getDomain().getCommon();

        BeanUtils.copyProperties(domainCreateBean.getDomain().getCommon(),geogrid);

        BeanUtils.copyProperties(domainCreateBean.getDomain().getWrf(),geogrid);

        map.put("geogrid",geogrid);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",templateDir,"namelist.wps.geogrid.template"),content);

    }

    private boolean generateNamelistwrf(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_wrf_template();

        //作用域
        Map map = new HashMap();

        Wrf wrf = new Wrf();

        CommonParams commonParams = domainCreateBean.getDomain().getCommon();

        WrfParams wrfParams = domainCreateBean.getDomain().getWrf();

        BeanUtils.copyProperties(commonParams,wrf);

        BeanUtils.copyProperties(wrfParams,wrf);

        map.put("wrf",wrf);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",templateDir,"namelist.wps.metgrid.template"),content);
    }

    private boolean generateNamelistwpsMetgrid(DomainCreateBean domainCreateBean){

        int max_dom = domainCreateBean.getDomain().getCommon().getMax_dom();

        String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_wps_metgrid_template();

        Map map = new HashMap();

        map.put("max_dom",max_dom);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",templateDir,"namelist.wps.metgrid.template"),content);
    }

}