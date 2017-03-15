package com.makenv.model.mc.server.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.server.message.controller.ModelController;
import com.makenv.model.mc.server.message.pojo.CommonParams;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import com.makenv.model.mc.server.message.pojo.WrfParams;
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

    private Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private McConfigManager mcConfigManager;

    private String templateDir;

    @PostConstruct
    public void postContruct(){

        templateDir = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getTemplate().getDirPath();
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

        boolean meicCacheFlag = generateMeicCacheConfTemplate(domainCreateBean);

        logger.info("generateMeicCacheConfTemplate is " + (meicCacheFlag ? "success":"failed"));

        boolean meicServerFlag = generateMeicServerConfTemplate(domainCreateBean);

        logger.info("generateMeicServerConfTemplate is " + (meicServerFlag ? "success":"failed"));

        return ipwrfFlag && oaFlag && wpsGeogridFlag && wrfFlag && metgridFlag;

    }

    private boolean generateMeicServerConfTemplate(DomainCreateBean domainCreateBean) {

        String meicTemplatePath = mcConfigManager.getSystemConfig().getTemplate().getMeic_server_conf_vm();

        TaskDomain domain = domainCreateBean.getDomain();

        String model = domainCreateBean.getDomain().getMeic().getModel().getName();

        String submodel = domainCreateBean.getDomain().getMeic().getModel().getSubmodel();

        String dx[]  =  domain.getCommon().getDx().split(",");

        String dy[] = domain.getCommon().getDy().split(",");

        String nx[] = domain.getCmaq().getNx().split(",");

        String ny[] = domain.getCmaq().getNy().split(",");

        String xorig[] = domain.getCmaq().getXorig().split(",");

        String yorig[] = domain.getCmaq().getXorig().split(",");

        int maxDom = domainCreateBean.getDomain().getCommon().getMax_dom();

        int currdom = maxDom;

        boolean flag = false;

        while(currdom-- > 0) {

            Map paramsMap = new HashMap();

            MeicServerConfBean meic = new MeicServerConfBean();

            meic.setStand_lat1(domain.getCommon().getStand_lat1());

            meic.setMeicType(Constant.MEIC_SERVER_TYPE);

            meic.setStand_lat2(domain.getCommon().getStand_lat2());

            meic.setRef_lat(domain.getCommon().getRef_lat());

            meic.setRef_lon(domain.getCommon().getRef_lon());

            meic.setDx(Integer.parseInt(dx[currdom].trim()));

            meic.setDy(Integer.parseInt(dy[currdom].trim()));

            meic.setXcells(Integer.parseInt(nx[currdom].trim()));

            meic.setYcells(Integer.parseInt(ny[currdom].trim()));

            meic.setXorig(Double.parseDouble(xorig[currdom].trim()));

            meic.setYorig(Double.parseDouble(yorig[currdom].trim()));

            meic.setName(String.format(Constant.GRIDNAME,currdom + 1));

            meic.setCurrdom(currdom + 1);

            meic.setModel(model);

            meic.setSubmodel(submodel);

            paramsMap.put("meic",meic);

            String content = VelocityUtil.buildTemplate(meicTemplatePath,paramsMap);

            String meicTemplate = FilePathUtil.joinByDelimiter(getTemplateDirName(domainCreateBean),String.format(Constant.MEIC_CONF_TEMPLATE,currdom + 1,Constant.MEIC_SERVER_TYPE));

            flag = FileUtil.save(meicTemplate,content);

        }


        return flag;
    }

    private boolean generateMeicCacheConfTemplate(DomainCreateBean domainCreateBean) {

        String meicTemplatePath = mcConfigManager.getSystemConfig().getTemplate().getMeic_cache_conf_vm();

        TaskDomain domain = domainCreateBean.getDomain();

        String dx[]  =  domain.getCommon().getDx().split(",");

        String dy[] = domain.getCommon().getDy().split(",");

        String nx[] = domain.getCmaq().getNx().split(",");

        String ny[] = domain.getCmaq().getNy().split(",");

        String xorig[] = domain.getCmaq().getXorig().split(",");

        String yorig[] = domain.getCmaq().getXorig().split(",");

        int maxDom = domainCreateBean.getDomain().getCommon().getMax_dom();

        int currdom = maxDom;

        boolean flag = false;

        while(currdom-- > 0) {

            Map paramsMap = new HashMap();

            MeicServerConfBean meic = new MeicServerConfBean();

            meic.setStand_lat1(domain.getCommon().getStand_lat1());

            meic.setStand_lat2(domain.getCommon().getStand_lat2());

            meic.setRef_lat(domain.getCommon().getRef_lat());

            meic.setRef_lon(domain.getCommon().getRef_lon());

            meic.setDx(Integer.parseInt(dx[currdom].trim()));

            meic.setDy(Integer.parseInt(dy[currdom].trim()));

            meic.setXcells(Integer.parseInt(nx[currdom].trim()));

            meic.setYcells(Integer.parseInt(ny[currdom].trim()));

            meic.setName(String.format(Constant.GRIDNAME,currdom + 1));

            meic.setXorig(Double.parseDouble(xorig[currdom].trim()));

            meic.setYorig(Double.parseDouble(yorig[currdom].trim()));

            meic.setMeicType(Constant.MEIC_CACHE_TYPE);

            meic.setCurrdom(currdom + 1);

            paramsMap.put("meic",meic);

            String content = VelocityUtil.buildTemplate(meicTemplatePath,paramsMap);

            String meicTemplate = FilePathUtil.joinByDelimiter(getTemplateDirName(domainCreateBean),String.format(Constant.MEIC_CONF_TEMPLATE,currdom + 1,Constant.MEIC_CACHE_TYPE));

            flag = FileUtil.save(meicTemplate,content);

        }

        return flag;
    }

    private boolean generateNamelistIpwrf(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfig().getTemplate().getNamelist_ipxwrf();

        boolean flag = true;

        try {

            FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/",getTemplateDirName(domainCreateBean), Constant.NAMELIST_IPXWRF_TEMPLATE));

        } catch (IOException e) {

            flag = false;

            e.printStackTrace();
        }

        return flag;

    }

    private boolean generateNamelistOa(DomainCreateBean domainCreateBean) {

        String filePathName = mcConfigManager.getSystemConfig().getTemplate().getNamelist_oa();

        boolean flag = true;

        try {

            FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/",getTemplateDirName(domainCreateBean),Constant.NAMELIST_OA_TEMPLATE));

        } catch (IOException e) {

            flag = false;

            e.printStackTrace();
        }

        return flag;

    }

    private boolean generateNamelistwpsGeogrid(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfig().getTemplate().getNamelist_wps_geogrid();

        Map map = new HashMap<>();

        Geogrid geogrid = new Geogrid();

        CommonParams commonParams = domainCreateBean.getDomain().getCommon();

        BeanUtils.copyProperties(domainCreateBean.getDomain().getCommon(),geogrid);

        BeanUtils.copyProperties(domainCreateBean.getDomain().getWrf(),geogrid);

        //dx //dy 只取最大值
        String dx[] = commonParams.getDx().split(",");

        String dy[] = commonParams.getDx().split(",");

        geogrid.setDx(dx[0]);

        geogrid.setDy(dy[0]);

        map.put("geogrid",geogrid);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",getTemplateDirName(domainCreateBean),Constant.NAMELIST_WPS_GEOGRID_TEMPLATE),content);

    }

    private boolean generateNamelistwrf(DomainCreateBean domainCreateBean){

        String filePathName = mcConfigManager.getSystemConfig().getTemplate().getNamelist_wrf();

        //作用域
        Map map = new HashMap();

        Wrf wrf = new Wrf();

        CommonParams commonParams = domainCreateBean.getDomain().getCommon();

        WrfParams wrfParams = domainCreateBean.getDomain().getWrf();

        BeanUtils.copyProperties(commonParams,wrf);

        BeanUtils.copyProperties(wrfParams,wrf);

        map.put("wrf",wrf);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",getTemplateDirName(domainCreateBean),Constant.NAMELIST_WRF_TEMPLATE),content);
    }

    private boolean generateNamelistwpsMetgrid(DomainCreateBean domainCreateBean){

        int max_dom = domainCreateBean.getDomain().getCommon().getMax_dom();

        String filePathName = mcConfigManager.getSystemConfig().getTemplate().getNamelist_wps_metgrid();

        Map map = new HashMap();

        map.put("max_dom",max_dom);

        String content = VelocityUtil.buildTemplate(filePathName,map);

        return  FileUtil.save(FilePathUtil.joinByDelimiter("/",getTemplateDirName(domainCreateBean),Constant.NAMELIST_WPS_METGRID_TEMPLATE),content);
    }

    private String getTemplateDirName(DomainCreateBean domainCreateBean) {

        String filePathName = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid()
                .getCommon().getTemplate().getDirPath();

        return filePathName.replaceAll("\\{userid\\}",domainCreateBean.getUserid()).
                replaceAll("\\{domainid\\}",domainCreateBean.getDomainid());
    }

}