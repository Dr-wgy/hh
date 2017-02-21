package com.makenv.model.mc.cli;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.func.IOperator;
import com.makenv.model.mc.cli.func.OperatorFactory;
import com.makenv.model.mc.cli.helper.CommandHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alei on 2017/2/21.
 */
public class Application {
  private static Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {

    CommandHelper.init(args);
    String funcType = CommandHelper.getValueAndCheck(CommandType.CMD_TYPE);
    IOperator operator = OperatorFactory.getOperator(funcType);
    if (operator == null) {
      logger.error(String.format("params -%s value( %s ) invalid", CommandType.CMD_TYPE.opt, funcType));
      return;
    }
    operator.operate();
  }
}
