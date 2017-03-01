package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.TaskDomain;
import com.makenv.model.mc.velocity.GriddescBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/3/1.
 */
@Component
public class GriddescHelper {

    @Autowired
    private McConfigManager mcConfigManager;

    public boolean generateGriddesc(DomainCreateBean domainCreateBean) {

        TaskDomain domain = domainCreateBean.getDomain();

        String dx[]  =  domain.getCommon().getDx().split(",");

        String dy[] = domain.getCommon().getDy().split(",");

        String nx[] = domain.getCmaq().getNx().split(",");

        String ny[] = domain.getCmaq().getNy().split(",");

        String xorig[] = domain.getCmaq().getXorig().split(",");

        String yorig[] = domain.getCmaq().getXorig().split(",");

        int maxDom = domainCreateBean.getDomain().getCommon().getMax_dom();

        String griddescFilePathName = getgGriddescFilePathName(domainCreateBean);

        String gridescVmFile = mcConfigManager.getSystemConfigPath().getTemplate().getGriddesc();

        boolean flag = false;

        while(maxDom > 0) {

            GriddescBean griddescBean = new GriddescBean();

            griddescBean.setMap_proj(domain.getCommon().getMap_proj());

            griddescBean.setRef_lat(domain.getCommon().getRef_lat());

            griddescBean.setRef_lon(domain.getCommon().getRef_lon());

            griddescBean.setStand_lat1(domain.getCommon().getStand_lat1());

            griddescBean.setStand_lat2(domain.getCommon().getStand_lat2());

            griddescBean.setXorig(Double.parseDouble(xorig[maxDom].trim()));

            griddescBean.setYorig(Double.parseDouble(yorig[maxDom].trim()));

            griddescBean.setDx(Double.parseDouble(dx[maxDom].trim()));

            griddescBean.setDy(Double.parseDouble(dy[maxDom].trim()));

            griddescBean.setNx(Integer.parseInt(nx[maxDom].trim()));

            griddescBean.setNy(Integer.parseInt(ny[maxDom].trim()));

            String currDomFile = String.format(griddescFilePathName, maxDom);

            Map map = new HashMap();

            map.put("griddescBean",griddescBean);

            String content = VelocityUtil.buildTemplate(gridescVmFile,map);

            flag = FileUtil.save(content,currDomFile);

            -- maxDom;
        }

        return flag;
    }

    private String getgGriddescFilePathName(DomainCreateBean domainCreateBean) {

        String filePathName = mcConfigManager.getSystemConfigPath().
                getWorkspace().getUserid().getDomainid().
                getCommon().getData().getGriddesc().getFilePath();

        return filePathName.replaceAll("\\{userid\\}",domainCreateBean.getUserid()).
                replaceAll("\\{domainid\\}",domainCreateBean.getDomainid());
    }
}
