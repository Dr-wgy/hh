package com.makenv.model.mc.cli.cmd;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.exception.InvalidParamsException;
import com.makenv.model.mc.cli.func.OperatorFactory;
import com.makenv.model.mc.core.util.StringUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by alei on 2016/8/31.
 */
@Component
public class CommandManager {
  private Options options = new Options();
  private CommandLine line;
  @Autowired
  private OperatorFactory operatorFactory;
  private ApplicationArguments args;
  private Logger logger = LoggerFactory.getLogger(CommandManager.class);

  @Autowired
  public CommandManager(ApplicationArguments args) throws Exception {
    this.args = args;
  }

  @PostConstruct
  void initAndProcess() throws Exception {
    for (CommandType cmdType : CommandType.values()) {
      Option input = new Option(String.valueOf(cmdType.opt), cmdType.longOpt, cmdType.hasArg, cmdType.description);
      input.setRequired(cmdType.required);
      options.addOption(input);
    }
    if (parseAndCheck(args.getSourceArgs())) {
      operatorFactory.getOperator(getValue(CommandType.CMD_TYPE)).operate();
    }
  }

  private boolean parseAndCheck(String[] args) throws Exception {
    if (Arrays.isNullOrEmpty(args)) {
      printHelp();
      return false;
    }
    CommandLineParser parser = new DefaultParser();
    // parse the command line arguments
    line = parser.parse(options, args);
    for (Option option : options.getOptions()) {
      if (option.isRequired() && !line.hasOption(option.getOpt())) {
        printHelp();
        logger.error(String.format("parameters %s is required", option.getOpt()));
        return false;
      }
    }
    return true;
  }

  public String getValue(String option) {
    if (line.hasOption(option)) {
      return line.getOptionValue(option);
    }
    return null;
  }

  String getValue(char opt) {
    if (line.hasOption(opt)) {
      return line.getOptionValue(opt);
    }
    return null;
  }

  String getValue(CommandType commandType) {
    return getValue(commandType.opt);
  }

  public String getValueAndCheck(CommandType commandType) throws InvalidParamsException {
    String value = getValue(commandType);
    if (StringUtil.isEmpty(value)) {
      onMissingParameters(commandType.opt);
    }
    return value;
  }

  public void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("mc", options);
  }

  private void onMissingParameters(char param) throws InvalidParamsException {
    printHelp();
    throw new InvalidParamsException(StringUtil.formatLog("missing parameter -" + param));
  }

}
