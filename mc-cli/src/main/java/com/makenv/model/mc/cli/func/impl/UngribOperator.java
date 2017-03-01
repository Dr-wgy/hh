package com.makenv.model.mc.cli.func.impl;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.func.AbstractOperator;
import com.makenv.model.mc.cli.cmd.CommandManager;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
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

  @Override
  public String getName() {
    return "UngribOperator";
  }

  @Override
  protected boolean beforeOperate() throws Exception {
    date = commandManager.getValueAndCheck(CommandType.CMD_DATE);
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
    return false;
  }

  private boolean checkGfs() {
    return false;
  }

  @Override
  protected boolean doOperate() throws Exception {
    copyFiles();
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

  private void copyFiles() throws IOException {
    String fnlSrc = "", fnlDest = "", gfsSrc = "", gfsDest = "";
    FileUtil.copyFolder(new File(fnlSrc), new File(fnlDest));
    FileUtil.copyFolder(new File(gfsSrc), new File(gfsDest));
  }

  @Override
  protected boolean afterOperate() throws Exception {
    return true;
  }
}
