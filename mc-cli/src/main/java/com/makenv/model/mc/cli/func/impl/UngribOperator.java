package com.makenv.model.mc.cli.func.impl;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.func.AbstractOperator;
import com.makenv.model.mc.cli.helper.CommandHelper;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;

import java.io.File;
import java.time.LocalDate;

/**
 * Created by alei on 2017/2/21.
 */
public class UngribOperator extends AbstractOperator {
  private String date;

  @Override
  public String getName() {
    return "UngribOperator";
  }

  @Override
  protected void beforeOperate() throws Exception {
    String date = CommandHelper.getValueAndCheck(CommandType.CMD_DATE);
    if (StringUtil.isEmpty(date)) {
      date = LocalTimeUtil.formatToday("yyyyMMdd");
    }
    if (checkFnl()) {

    }
    if (checkGfs()) {

    }
  }

  private boolean checkFnl() {
    return false;
  }

  private boolean checkGfs() {
    return false;
  }

  @Override
  protected void doOperate() throws Exception {
    copyFiles();
    buildEnv();
    execFnl();
    execGfs();
  }

  private void buildEnv() {

  }

  private void execFnl() {

  }

  private void execGfs() {

  }

  private boolean copyFiles() {
    String fnlSrc = "", fnlDest = "", gfsSrc = "", gfsDest = "";
    if (!FileUtil.copyFolder(new File(fnlSrc), new File(fnlDest))) return false;
    if (!FileUtil.copyFolder(new File(gfsSrc), new File(gfsDest))) return false;
    return true;
  }

  @Override
  protected void afterOperate() throws Exception {

  }
}
