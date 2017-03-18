package com.makenv.model.mc.server.message.helper;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.*;
import com.makenv.model.mc.server.config.Cmd;
import com.makenv.model.mc.server.constant.Constants;
import com.makenv.model.mc.server.constant.PBSStatus;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.service.impl.ModelServiceImpl;
import com.makenv.model.mc.server.message.util.ShellResult;
import com.makenv.model.mc.server.message.util.XshellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by wgy on 2017/3/8.
 */
//执行geogrid和oceanFile
@Component
public class CreateDomainHelper {

  private Logger logger = LoggerFactory.getLogger(CreateDomainHelper.class);

  @Autowired
  private McConfigManager mcConfigManager;

  @Autowired
  private Cmd cmd;

  public boolean executeShell(DomainCreateBean domainCreateBean) {

    String qsubCmd = mcConfigManager.getSystemConfig().getPbs().getQsub();

    String moduleDomainCsh = mcConfigManager.getSystemConfig().getCsh().getModule_domain_csh();

    String script_path = mcConfigManager.getSystemConfig().getRoot().getScript();

    String geogridRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getGeogrid();

    geogridRunPath = replaceRegex(geogridRunPath, domainCreateBean);

    String renvPathName = buildRenv(domainCreateBean);

    String invokeFile = prepareExecShell(geogridRunPath, script_path, moduleDomainCsh, renvPathName);

    String errorLog = FilePathUtil.joinByDelimiter(geogridRunPath, Constants.GEOGRID_ERROR_FILE_NAME);

    String infoLog = FilePathUtil.joinByDelimiter(geogridRunPath, Constants.GEOGRID_ERROR_FILE_NAME);

    try {

      qsubCmd = String.format(qsubCmd, 1,
          1, String.join("",Constants.GEOGRID_NAME, domainCreateBean.getDomainid()),
          errorLog,infoLog, invokeFile);

      logger.info(qsubCmd);

      Runtime.getRuntime().exec(qsubCmd);

    } catch (Exception e) {

      e.printStackTrace();

      return false;
    }

    return true;
  }

  private String buildRenv(DomainCreateBean domainCreateBean) {

    String renvDomainsh = mcConfigManager.getSystemConfig().getTemplate().getRenv_domain_sh();

    String geogridTemplatePath = FilePathUtil.joinByDelimiter(mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid()
        .getCommon().getTemplate().getDirPath(), Constant.NAMELIST_WPS_GEOGRID_TEMPLATE);

    geogridTemplatePath = replaceRegex(geogridTemplatePath, domainCreateBean);
    GeoRenv geoRenv = new GeoRenv();
    geoRenv.setWrf_build_path(mcConfigManager.getSystemConfig().getRoot().getWrf());
    geoRenv.setScripts_path(mcConfigManager.getSystemConfig().getRoot().getScript());
    geoRenv.setNamelist_wps_geogrid_template(geogridTemplatePath);
    geoRenv.setGeog_data_path(mcConfigManager.getSystemConfig().getWorkspace().getShare().getInput().getGeog().getDirPath());
    geoRenv.setWrf_version(domainCreateBean.getDomain().getWrf().getVersion());

    String geogridDataPath = mcConfigManager.getSystemConfig()
        .getWorkspace().getUserid().
            getDomainid().getCommon().
            getData().getGeogrid().getDirPath();

    geogridDataPath = replaceRegex(geogridDataPath, domainCreateBean);
    //创建文件夹
    FileUtil.createFolder(geogridDataPath);
    geoRenv.setGeogrid_data_path(geogridDataPath);
    HashMap hashMap = new HashMap();
    hashMap.put("georenv", geoRenv);
    String content = VelocityUtil.buildTemplate(renvDomainsh, hashMap);

    String geogridRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getGeogrid();
    geogridRunPath = replaceRegex(geogridRunPath, domainCreateBean);

    String renvPathName = FilePathUtil.joinByDelimiter(geogridRunPath, Constant.DOMAIN_RENV_FILE);

    FileUtil.save(renvPathName, content);

    return renvPathName;


  }

  //准备执行的脚本
  private String prepareExecShell(String geogridRunPath, String script_path, String moduleDomainCsh, String renvPathName) {

    StringBuffer stringBuffer = new StringBuffer("#!/usr/bin/csh -f");
    stringBuffer.append("\n")
        .append("cd ")
        .append(geogridRunPath)
        .append("\n")
        .append("source " + mcConfigManager.getSystemConfig().getRenv().getSys())
        .append("\n")
        .append(moduleDomainCsh).append(" ")
        .append(renvPathName);

    String invokeFile = FilePathUtil.joinByDelimiter(geogridRunPath, Constant.GEOGRID_SCRIPT_FILE);

    File file = new File(invokeFile);

    try {

      FileUtil.writeLocalFile(file, stringBuffer.toString());

    } catch (IOException e) {

      e.printStackTrace();
    }

    file.setExecutable(true);

    return invokeFile;

  }

  private String replaceRegex(String path, DomainCreateBean domainCreateBean) {

    return path.replaceAll("\\{userid\\}", domainCreateBean.getUserid()).replaceAll("\\{domainid\\}", domainCreateBean.getDomainid());
  }

}
