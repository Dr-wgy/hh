package com.makenv.model.mc.cli.func.impl;

import com.makenv.model.mc.cli.cmd.CommandManager;
import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.exception.InvalidParamsException;
import com.makenv.model.mc.cli.func.AbstractOperator;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.makenv.model.mc.core.constant.Constant.*;

/**
 * Created by alei on 2017/2/21.
 */
@Service
public class UngribOperator extends AbstractOperator {
  private String computeDate;
  @Autowired
  private CommandManager commandManager;
  @Autowired
  private McConfigManager configManager;

  private static final String DATE_FORMAT = "yyyyMMdd";

  private String invokeScriptFile, logFile, namelistFile, tagFile, renvFile, invokeDir;

  private final static String TYPE_FNL = "fnl";
  private final static String TYPE_GFS = "gfs";

  private Logger logger = LoggerFactory.getLogger(UngribOperator.class);
  private String fnlDir, gfsDir, syncFnlDir, syncGfsDir, ungribFnlDir, ungribGfsDir;

  @Override
  public String getName() {
    return "UngribOperator";
  }

  @Override
  protected boolean beforeOperate() {
    try {
      computeDate = commandManager.getValueAndCheck(CommandType.CMD_DATE);
      if (StringUtil.isEmpty(computeDate)) {
        computeDate = LocalTimeUtil.formatToday(DATE_FORMAT);
      }
    } catch (InvalidParamsException e) {
      logger.error("", e);
      return false;
    }
    {
      String _year = computeDate.substring(0, 4);
      LocalDate today = LocalTimeUtil.parse(computeDate, DATE_FORMAT);
      LocalDate yesterday = today.plusDays(-1);
      String computeYesterday = LocalTimeUtil.format(yesterday, DATE_FORMAT);
      syncFnlDir = configManager.getSystemConfig().getSync().getFnl() + File.separator + _year;
      syncGfsDir = configManager.getSystemConfig().getSync().getGfs() + String.format("%s%s%s", File.separator, computeYesterday, Constant.START_HOUR);
      String _fnlDirSuffix = String.format("%s%s", File.separator, _year);
      String _gfsDirSuffix = String.format("%s%s%s", File.separator, computeYesterday, Constant.START_HOUR);
      fnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getFnl().getDirPath() + _fnlDirSuffix;
      gfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getGfs().getDirPath() + _gfsDirSuffix;
      ungribFnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath() + _fnlDirSuffix;
      ungribGfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath() + _gfsDirSuffix;

      String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();

      String tagDir = String.format("%s%stag%s", runPath, File.separator, File.separator);
      tagFile = String.format("%s%s", tagDir, computeDate);
      String renvDir = runPath + File.separator + "renv";
      renvFile = String.format("%s%s%s-%s", renvDir, File.separator, UNGRIB_RENV_FILE, computeDate);
      String logDir = runPath + File.separator + "log";
      logFile = String.format("%s%s%s-%s", logDir, File.separator, UNGRIB_LOG_FILE, computeDate);
      invokeDir = runPath + File.separator + "invoke";
      invokeScriptFile = String.format("%s%s%s-%s", invokeDir, File.separator, UNGRIB_SCRIPT_FILE, computeDate);


      FileUtil.checkAndMkdir(logDir);
      FileUtil.checkAndMkdir(tagDir);
      FileUtil.checkAndMkdir(renvDir);
      FileUtil.checkAndMkdir(invokeDir);
      FileUtil.checkAndMkdir(ungribFnlDir);
      FileUtil.checkAndMkdir(ungribGfsDir);

      namelistFile = String.format("%s%snamelist.wps.ungrib.template", invokeDir, File.separator);
      String namelist = configManager.getSystemConfig().getTemplate().getNamelist_wps_ungrib();
      File nlFile = new File(namelist);
      FileUtil.symbolicLink(nlFile.getAbsolutePath(), namelistFile);

      logger.info(fnlDir);
      logger.info(gfsDir);
      logger.info(syncFnlDir);
      logger.info(syncGfsDir);
      logger.info(ungribFnlDir);
      logger.info(ungribGfsDir);
      logger.info(invokeScriptFile);
      logger.info(logFile);
      logger.info(namelistFile);
      logger.info(tagFile);
      logger.info(renvFile);

      File tag = new File(tagFile);
      if (tag.exists()) {
        logger.info(String.format("ungrib already done, %s", tagFile));
        return true;
      }
    }
    if (!checkFnl()) {
      return false;
    }
    if (!checkGfs()) {
      return false;
    }
    return true;
  }

  private boolean checkFnl() {
    return true;
  }

  private boolean checkGfs() {
    return true;
  }

  @Override
  protected boolean doOperate() {
    try {
      buildEnv();
      prepareExecScript();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    if (!copyFiles()) return false;
    try {
      exec();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private void buildEnv() throws IOException {
    String renvTemplate = configManager.getSystemConfig().getTemplate().getRenv_ungrib_csh();
    Map<String, Object> params = new HashMap<>();
    params.put("namelist_template", namelistFile);
    params.put("start_date", computeDate);
    params.put("end_date", computeDate);
    params.put("start_hour", Constant.START_HOUR);
    params.put("ungrib_file", Constant.UNGRIB_FILE_PREFIX);
    params.put("debug", Constant.MODEL_DEBUG_LEVEL);
    params.put("fnl_input", fnlDir);
    params.put("gfs_input", gfsDir);
    params.put("fnl_output", ungribFnlDir);
    params.put("gfs_output", ungribGfsDir);
    params.put("scripts_path", configManager.getSystemConfig().getRoot().getScript());
    params.put("wrf_build_path", configManager.getSystemConfig().getRoot().getWrf());
    String content = VelocityUtil.buildTemplate(renvTemplate, params);
    FileUtil.writeLocalFile(new File(renvFile), content);
  }

  private void prepareExecScript() throws IOException {
    String sb = "#!/usr/bin/env bash\n" +
        "cd " +
        invokeDir +
        "\n" +
        buildCmd(TYPE_FNL) +
        buildCmd(TYPE_GFS);
    File file = new File(invokeScriptFile);
    FileUtil.writeLocalFile(file, sb);
    file.setExecutable(true);
  }

  private StringBuilder buildCmd(String type) {
    StringBuilder sb = new StringBuilder();
    String driverScriptPath = String.format("%s%slevel_3%sModule_ungrib.csh", configManager.getSystemConfig().getRoot().getScript(), File.separator, File.separator);
    sb.append(driverScriptPath);
    sb.append(" ");
    sb.append(renvFile);
    sb.append(" ");
    sb.append(type);
    sb.append("\n");
    return sb;
  }

  private void exec() throws IOException {
    String qsub = configManager.getSystemConfig().getPbs().getQsub();
    qsub = String.format(qsub, 1, 2, "ungrib-" + computeDate, logFile, invokeScriptFile);
    logger.info(qsub);
    Runtime.getRuntime().exec(qsub);
  }

  private boolean copyFiles() {
    try {
      for (String _hour : Constant.FILE_HOURS) {
        String _file = String.format("fnl_%s_%s_00.grib2", computeDate, _hour);
        String fnlSrcFile = String.format("%s%s%s", syncFnlDir, File.separator, _file);
        String fnlDestFile = String.format("%s%s%s", fnlDir, File.separator, _file);
        logger.info(fnlSrcFile + " -> " + fnlDestFile);
        FileUtil.copyFile(fnlSrcFile, fnlDestFile);
      }
      logger.info(syncGfsDir + " -> " + gfsDir);
      FileUtil.copyFolder(new File(syncGfsDir), new File(gfsDir));
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean afterOperate() {
//    String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    try {
//      String tagPath = String.format("%s%stag%s", runPath, File.separator, File.separator);
//      FileUtil.checkAndMkdir(tagPath);
      FileUtil.writeLocalFile(new File(tagFile), "");
    } catch (IOException e) {
      logger.error("", e);
    }
    return true;
  }
}
