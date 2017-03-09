package com.makenv.model.mc.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alei on 2017/2/21.
 */
@EnableAutoConfiguration
@ComponentScan({"com.makenv.model.mc.cli","com.makenv.model.mc.redis","com.makenv.model.mc.core"})
@Configuration
public class Application {
//  private static Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
    //java -jar mc-cli.jar -t ungrib -i /etc/xxx.conf
//    CommandHelper.init(args);
//    String funcType = CommandHelper.getValueAndCheck(CommandType.CMD_TYPE);
//    IOperator operator = OperatorFactory.getOperator(funcType);
//    if (operator == null) {
//      logger.error(String.format("params -%s value( %s ) invalid", CommandType.CMD_TYPE.opt, funcType));
//      return;
//    }
//    operator.operate();
  }
}
