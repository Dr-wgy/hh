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
    } catch (IOException e) {
      logger.error("", e);
      return false;
    }
    prepareExecScript();
    exec();
    return true;
  }

  private void buildEnv() throws IOException {
    String templateFile = configManager.getSystemConfigPath().getTemplate().getNamelist_wps_ungrib();
    Map<String, Object> params = new HashMap<>();
    params.put("namelist_template", templateFile);
    params.put("start_date", date);
    params.put("end_date", date);
    params.put("start_hour", Constant.START_HOUR);
    params.put("ungrib_file", Constant.UNGRIB_FILE_PREFIX);
    params.put("debug", Constant.MODEL_DEBUG_LEVEL);
    params.put("fnl_input", fnlDir);
    params.put("gfs_input", gfsDir);
    params.put("fnl_output", ungribFnlDir);
    params.put("gfs_output", ungribGfsDir);
    String content = VelocityUtil.buildTemplate(templateFile, params);
    FileUtil.writeLocalFile(new File(templateFile), content);
  }

  private void prepareExecScript() {

  }

  private void exec() {

  }

  private boolean copyFiles() {
    String _year = date.substring(0, 4);
    String _date = date.substring(4, 8);
    syncFnlDir = configManager.getSystemConfigPath().getSync().getFnl() + File.separator + _year;
    syncGfsDir = configManager.getSystemConfigPath().getSync().getGfs() + String.format("%s%s%s", File.separator, date, Constant.START_HOUR);
    String _fnlDirSuffix = String.format("%s%s%s%s", File.separator, _year, File.separator, _date);
    String _gfsDirSuffix = String.format("%s%s%s%s%s", File.separator, _year, File.separator, _date, Constant.START_HOUR);
    fnlDir = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getFnl().getDirPath() + _fnlDirSuffix;
    gfsDir = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getGfs().getDirPath() + _gfsDirSuffix;
    ungribFnlDir = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getUngrib_fnl().getDirPath() + _fnlDirSuffix;
    ungribGfsDir = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getUngrib_gfs().getDirPath() + _gfsDirSuffix;
    try {
      for (String _hour : Constant.FILE_HOURS) {
        String _file = String.format("fnl_%s_%s_00.grib2", date, _hour);
        String fnlSrcFile = String.format("%s%s%s", syncFnlDir, File.separator, _file);
        String fnlDestFile = String.format("%s%s%s", fnlDir, File.separator, _file);
        FileUtil.copyFile(fnlSrcFile, fnlDestFile);
        logger.info(fnlSrcFile + " -> " + fnlDestFile);
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
    return true;
  }
}
