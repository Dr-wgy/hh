package com.makenv.model.mc.cli.func;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.helper.CommandHelper;
import com.makenv.model.mc.cli.helper.JedisHelper;
import com.makenv.model.mc.core.util.StringUtil;

import java.io.File;

/**
 * Created by alei on 2017/2/21.
 */
public abstract class AbstractOperator implements IOperator {
  @Override
  public void init() throws Exception {
    String output = CommandHelper.getValueAndCheck(CommandType.CMD_OUTPUT);
    if (!StringUtil.isEmpty(output)) {
      File outputFile = new File(output);
      if (!outputFile.exists()) {
        outputFile.mkdirs();
      }
    }
  }

  @Override
  public void operate() throws Exception {
    beforeOperate();
    doOperate();
    afterOperate();
  }

  protected void sendMessage(String content) {
    JedisHelper.sendMessage(content);
  }

  protected abstract void beforeOperate() throws Exception;

  protected abstract void doOperate() throws Exception;

  protected abstract void afterOperate() throws Exception;
}
