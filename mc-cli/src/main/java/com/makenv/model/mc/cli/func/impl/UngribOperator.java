package com.makenv.model.mc.cli.func.impl;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.exception.InvalidParamsException;
import com.makenv.model.mc.cli.func.AbstractOperator;
import com.makenv.model.mc.cli.cmd.CommandManager;
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
    buildEnv();
    execFnl();
    execGfs();
    return true;
  }

  private void buildEnv() {
    String templateFile = "";
    Map<String, String> params = new HashMap<>();
    params.put("namelist_template", templateFile);
    params.put("start_date", date);
    params.put("end_date", date);
    params.put("start_hour", "");
    params.put("ungrib_file", "");
    params.put("debug", "");
    params.put("fnl_nput", "");
    params.put("gfs_input", "");
    params.put("fnl_output", "");
    params.put("gfs_output", "");
    VelocityUtil.buildTemplate(templateFile, params);
  }

  private void execFnl() {

  }

  private void execGfs() {

  }

  private boolean copyFiles() {
    String _year = date.substring(0, 4);
    String datePath = String.format("%s%s", File.separator, _year);
    String timePath = String.format("%s%s", datePath, Constant.START_HOUR);
    String fnlSrc = configManager.getSystemConfigPath().getSync().getFnl() + datePath;
    String fnlDest = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getFnl().getDirPath() + datePath;
    String gfsSrc = configManager.getSystemConfigPath().getSync().getGfs() + timePath;
    String gfsDest = configManager.getSystemConfigPath().getWorkspace().getShare().getInput().getGfs().getDirPath() + timePath;
    try {
      for (String _hour : Constant.FILE_HOURS) {
        String _file = String.format("fnl_%s_%s_00.grib2", date, _hour);
        String fnlSrcFile = String.format("%s%s%s", fnlSrc, File.separator, _file);
        String fnlDestFile = String.format("%s%s%s", fnlDest, File.separator, _file);
        FileUtil.copyFile(fnlSrcFile, fnlDestFile);
      }
      FileUtil.copyFolder(new File(gfsSrc), new File(gfsDest));
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
