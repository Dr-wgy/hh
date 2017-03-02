package com.makenv.model.mc.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.pojo.CommonParams;
import com.makenv.model.mc.message.pojo.DomainCreateBean;
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

  @Autowired
  private McConfigManager mcConfigManager;

  private String templateDir;

  @PostConstruct
  public void postContruct() {

    templateDir = mcConfigManager.getSystemConfigPath().getWorkspace().getUserid().getDomainid().getCommon().getTemplate().getDirPath();
  }


  public boolean generateNamelist(DomainCreateBean domainCreateBean) {

    generateNamelistIpwrf(domainCreateBean);

    generateNamelistOa(domainCreateBean);

    generateNamelistwpsGeogrid(domainCreateBean);

    generateNamelistwrf(domainCreateBean);

    generateNamelistwpsMetgrid(domainCreateBean);

    return false;

  }

  private boolean generateNamelistIpwrf(DomainCreateBean domainCreateBean) {

    String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_ipxwrf();

    boolean flag = true;

    try {

      FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/", templateDir, "namelist.ipxwrf.template"));

    } catch (IOException e) {

      flag = false;

      e.printStackTrace();
    }

    return flag;

  }

  private boolean generateNamelistOa(DomainCreateBean domainCreateBean) {

    String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_oa();

    boolean flag = true;

    try {

      FileUtil.copyFile(filePathName, FilePathUtil.joinByDelimiter("/", templateDir, "namelist.oa.template"));

    } catch (IOException e) {

      flag = false;

      e.printStackTrace();
    }

    return false;

  }

  private boolean generateNamelistwpsGeogrid(DomainCreateBean domainCreateBean) {

    String filePathName = mcConfigManager.getSystemConfigPath().getTemplate().getNamelist_wps_geogrid();

    Map map = new HashMap<>();

    Geogrid geogrid = new Geogrid();

    CommonParams commonParams = domainCreateBean.getDomain().getCommon();

    BeanUtils.copyProperties(domainCreateBean.getDomain().getCommon(), geogrid);

    BeanUtils.copyProperties(domainCreateBean.getDomain().getWrf(), geogrid);

    map.put("geogrid", geogrid);

    String content = VelocityUtil.buildTemplate(filePathName, map);

    return FileUtil.save(FilePathUtil.joinByDelimiter("/", templateDir, "namelist.wps.geogrid.template"), content);

  }

  private boolean generateNamelistwrf(DomainCreateBean domainCreateBean) {


    return false;
  }

  private boolean generateNamelistwpsMetgrid(DomainCreateBean domainCreateBean) {


    return false;
  }

}
