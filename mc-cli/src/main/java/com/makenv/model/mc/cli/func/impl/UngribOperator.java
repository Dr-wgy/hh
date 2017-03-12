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

  private String invokeScriptFile, infoLogFile, errorLogFile, namelistFile, tagFile, renvGfsFile, renvFnlFile, runPath;

  private Logger logger = LoggerFactory.getLogger(UngribOperator.class);
  private String fnlDir, gfsDir, syncFnlDir, syncGfsDir, ungribFnlDir, ungribGfsDir, year;

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
      year = computeDate.substring(0, 4);
      LocalDate today = LocalTimeUtil.parse(computeDate, DATE_FORMAT);
      LocalDate yesterday = today.plusDays(-1);
      String computeYesterday = LocalTimeUtil.format(yesterday, DATE_FORMAT);
      syncFnlDir = configManager.getSystemConfig().getSync().getFnl() + File.separator + year;
      syncGfsDir = configManager.getSystemConfig().getSync().getGfs() + String.format("%s%s%s", File.separator, computeYesterday, configManager.getSystemConfig().getModel().getStart_hour());
      String _gfsDirSuffix = String.format("%s%s%s", File.separator, computeYesterday, configManager.getSystemConfig().getModel().getStart_hour());
      fnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getFnl().getDirPath();
      gfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getGfs().getDirPath() + _gfsDirSuffix;
      ungribFnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath();
      ungribGfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath() + _gfsDirSuffix;

      runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
      runPath = String.format("%s%s%s", runPath, File.separator, computeDate);

      tagFile = String.format("%s%stag", runPath, File.separator);
      renvFnlFile = String.format("%s%s%s-%s", runPath, File.separator, MODEL_RENV_FILE, Constant.GLOBAL_TYPE_FNL);
      renvGfsFile = String.format("%s%s%s-%s", runPath, File.separator, MODEL_RENV_FILE, Constant.GLOBAL_TYPE_GFS);
      infoLogFile = String.format("%s%s%s", runPath, File.separator, TORQUE_LOG_INFO);
      errorLogFile = String.format("%s%s%s", runPath, File.separator, TORQUE_LOG_ERROR);
      invokeScriptFile = String.format("%s%s%s", runPath, File.separator, Constant.MODEL_SCRIPT_FILE);

      FileUtil.checkAndMkdir(runPath);
      FileUtil.checkAndMkdir(ungribFnlDir);
      FileUtil.checkAndMkdir(ungribGfsDir);

      namelistFile = String.format("%s%s%s", runPath, File.separator, NAMELIST_WPS_UNGRIB_TEMPLATE);
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
      logger.info(errorLogFile);
      logger.info(namelistFile);
      logger.info(tagFile);
      logger.info(renvFnlFile);
      logger.info(renvGfsFile);

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

  private Map<String, Object> createParams() {
    Map<String, Object> params = new HashMap<>();
    params.put("namelist_template", namelistFile);
    params.put("start_date", computeDate);
    params.put("ungrib_file", Constant.UNGRIB_FILE_PREFIX);
    params.put("scripts_path", configManager.getSystemConfig().getRoot().getScript());
    params.put("wrf_build_path", configManager.getSystemConfig().getRoot().getWrf());
    params.put("debug", configManager.getSystemConfig().getModel().getDebug_level());
    return params;
  }

  private void buildEnv() throws IOException {
    String renvTemplate = configManager.getSystemConfig().getTemplate().getRenv_ungrib_sh();
    String content = VelocityUtil.buildTemplate(renvTemplate, buildFnlEnv());
    FileUtil.writeLocalFile(new File(renvFnlFile), content);
    content = VelocityUtil.buildTemplate(renvTemplate, buildGfsEnv());
    FileUtil.writeLocalFile(new File(renvGfsFile), content);
  }

  private Map<String, Object> buildFnlEnv() throws IOException {
    Map<String, Object> params = createParams();
    params.put("run_days", 1);
    params.put("input_path", fnlDir);
    params.put("output_path", ungribFnlDir);
    params.put("global", Constant.GLOBAL_TYPE_FNL);
    return params;
  }

  private Map<String, Object> buildGfsEnv() throws IOException {
    Map<String, Object> params = createParams();
    params.put("run_days", configManager.getSystemConfig().getModel().getUngrib_gfs_days());
    params.put("input_path", gfsDir);
    params.put("output_path", ungribGfsDir);
    params.put("global", Constant.GLOBAL_TYPE_GFS);
    return params;
  }

  private void prepareExecScript() throws IOException {
    String sourceSysRenv = String.format("source %s%s%s\necho $LD_LIBRARY_PATH\n\n", configManager.getSystemConfig().getRoot().getScript(), File.separator, Constant.SYS_RENV_CSH);
    String cdInvokeDir = String.format("cd %s\n", runPath);
    String sb = Constant.CSH_HEADER + sourceSysRenv +
        cdInvokeDir +
        buildCmd(Constant.GLOBAL_TYPE_FNL) +
        buildCmd(Constant.GLOBAL_TYPE_GFS);
    File file = new File(invokeScriptFile);
    FileUtil.writeLocalFile(file, sb);
    file.setExecutable(true);
  }

  private StringBuilder buildCmd(String type) {
    StringBuilder sb = new StringBuilder();
//    String driverScriptPath = String.format("%s%slevel_3%sModule_ungrib.csh", configManager.getSystemConfig().getRoot().getScript(), File.separator, File.separator);
    sb.append(configManager.getSystemConfig().getCsh().getModule_ungrib_csh());
    sb.append(" ");
    sb.append(type.equals(Constant.GLOBAL_TYPE_GFS) ? renvGfsFile : renvFnlFile);
    sb.append("\n");
    return sb;
  }

  private void exec() throws IOException {
    String qsub = configManager.getSystemConfig().getPbs().getQsub();
    qsub = String.format(qsub, 1, 1, "ungrib-" + computeDate, infoLogFile, errorLogFile, invokeScriptFile);
    logger.info(qsub);
    Runtime.getRuntime().exec(qsub);
  }

  private boolean copyFiles() {
    try {
      for (String _hour : Constant.FILE_HOURS) {
        String _file = String.format("fnl_%s_%s_00.grib2", computeDate, _hour);
        String fnlSrcFile = String.format("%s%s%s", syncFnlDir, File.separator, _file);
        String fnlDestFile = String.format("%s%s%s%s%s", fnlDir, File.separator, year, File.separator, _file);
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
    try {
      FileUtil.writeLocalFile(new File(tagFile), "");
    } catch (IOException e) {
      logger.error("", e);
    }
    return true;
  }
}
