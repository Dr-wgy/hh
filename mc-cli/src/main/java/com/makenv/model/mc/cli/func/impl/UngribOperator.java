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
import java.util.HashMap;
import java.util.Map;

import static com.makenv.model.mc.core.constant.Constant.UNGRIB_RENV_FILE;

/**
 * Created by alei on 2017/2/21.
 */
@Service
public class UngribOperator extends AbstractOperator {
  private String date;
  @Autowired
  private CommandManager commandManager;
  @Autowired
  private McConfigManager configManager;

  private String scriptPath;

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
      date = commandManager.getValueAndCheck(CommandType.CMD_DATE);
    } catch (InvalidParamsException e) {
      logger.error("", e);
      return false;
    }
    {
      String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
      String tagPath = String.format("%s%stag%s%s", runPath, File.separator, File.separator, date);
      File tag = new File(tagPath);
      if (tag.exists()) {
        logger.info(String.format("ungrib already done, %s", tagPath));
        return true;
      }
    }
    if (StringUtil.isEmpty(date)) {
      date = LocalTimeUtil.formatToday("yyyyMMdd");
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
    if (!copyFiles()) return false;
    try {
      buildEnv();
      prepareExecScript();
      exec();
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    return true;
  }

  private void buildEnv() throws IOException {
    String target = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    File targetDir = new File(target);
    if (!targetDir.exists()) {
      targetDir.mkdirs();
    }
    String renvTemplate = configManager.getSystemConfig().getTemplate().getRenv_ungrib_csh();
    Map<String, Object> params = new HashMap<>();
    params.put("namelist_template", String.format("%s%snamelist.wps.ungrib.template", target, File.separator));
    params.put("start_date", date);
    params.put("end_date", date);
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
    String renvPath = String.format("%s%s%s", target, File.separator, UNGRIB_RENV_FILE);
    FileUtil.writeLocalFile(new File(renvPath), content);
  }

  private void prepareExecScript() throws IOException {
    String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    String sb = "#!/usr/bin/env bash\n" +
        "cd " +
        runPath +
        "\n" +
        buildCmd(TYPE_FNL) +
        buildCmd(TYPE_GFS);
    scriptPath = String.format("%s%s%s-%s", runPath, File.separator, Constant.UNGRIB_SCRIPT_FILE, date);
    FileUtil.writeLocalFile(new File(scriptPath), sb);
  }

  private StringBuilder buildCmd(String type) {
    String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    StringBuilder sb = new StringBuilder();
    String driverScriptPath = String.format("%s%slevel_3%sModule_ungrib.csh", configManager.getSystemConfig().getRoot().getScript(), File.separator, File.separator);
    sb.append(driverScriptPath);
    sb.append(" ");
    sb.append(String.format("%s%s%s", runPath, File.separator, UNGRIB_RENV_FILE));
    sb.append(" ");
    sb.append(type);
    sb.append("\n");
    return sb;
  }

  private void exec() throws IOException {
    String qsub = configManager.getSystemConfig().getPbs().getQsub();
    String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    String logFile = String.format("%s%sungrib-%s.log", runPath, File.separator, date);
    qsub = String.format(qsub, 1, 2, "ungribe-" + date, logFile, scriptPath);
    Runtime.getRuntime().exec(qsub);
  }

  private boolean copyFiles() {
    String _year = date.substring(0, 4);
//    String _date = date.substring(4, 8);
    syncFnlDir = configManager.getSystemConfig().getSync().getFnl() + File.separator + _year;
    syncGfsDir = configManager.getSystemConfig().getSync().getGfs() + String.format("%s%s%s", File.separator, date, Constant.START_HOUR);
    String _fnlDirSuffix = String.format("%s%s", File.separator, _year);
    String _gfsDirSuffix = String.format("%s%s%s", File.separator, date, Constant.START_HOUR);
    fnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getFnl().getDirPath() + _fnlDirSuffix;
    gfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getGfs().getDirPath() + _gfsDirSuffix;
    ungribFnlDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath() + _fnlDirSuffix;
    ungribGfsDir = configManager.getSystemConfig().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath() + _gfsDirSuffix;
    try {
      for (String _hour : Constant.FILE_HOURS) {
        String _file = String.format("fnl_%s_%s_00.grib2", date, _hour);
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
    String runPath = configManager.getSystemConfig().getWorkspace().getShare().getRun().getUngrib().getDirPath();
    try {
      String tagPath = String.format("%s%stag%s", runPath, File.separator, File.separator);
      FileUtil.checkAndMkdir(tagPath);
      FileUtil.writeLocalFile(new File(tagPath + date), "");
    } catch (IOException e) {
      logger.error("", e);
    }
    return true;
  }
}
