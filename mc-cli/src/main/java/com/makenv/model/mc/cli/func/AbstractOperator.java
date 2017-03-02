package com.makenv.model.mc.cli.func;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.cmd.CommandManager;
import com.makenv.model.mc.cli.helper.JedisHelper;
import com.makenv.model.mc.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by alei on 2017/2/21.
 */
public abstract class AbstractOperator implements IOperator {
  @Autowired
  private CommandManager commandManager;
  @Autowired
  private JedisHelper jedisHelper;

  @Override
  public void init() throws Exception {
    String output = commandManager.getValueAndCheck(CommandType.CMD_OUTPUT);
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
    jedisHelper.sendMessage(content);
  }

  protected abstract boolean beforeOperate();

  protected abstract boolean doOperate();

  protected abstract boolean afterOperate();
}
