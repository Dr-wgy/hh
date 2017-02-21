package com.makenv.model.mc.cli.helper;

import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.exception.InvalidParamsException;
import com.makenv.model.mc.core.util.StringUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Created by alei on 2016/8/31.
 */
public class CommandHelper {
  private static Options options = new Options();
  private static CommandLine line;

  public static void init(String[] args) throws Exception {
    for (CommandType cmdType : CommandType.values()) {
      Option input = new Option(String.valueOf(cmdType.opt), cmdType.longOpt, cmdType.hasArg, cmdType.description);
      input.setRequired(cmdType.required);
      options.addOption(input);
    }
    parseAndCheck(args);
  }

  private static void parseAndCheck(String[] args) throws Exception {
    CommandLineParser parser = new DefaultParser();
    // parse the command line arguments
    line = parser.parse(options, args);
    for (Option option : options.getOptions()) {
      if (option.isRequired() && !line.hasOption(option.getOpt())) {
        printHelp();
        throw new InvalidParamsException(String.format("parameters %s is required", option.getOpt()));
      }
    }
  }

  public static String getValue(String option) {
    if (line.hasOption(option)) {
      return line.getOptionValue(option);
    }
    return null;
  }

  public static String getValue(char opt) {
    if (line.hasOption(opt)) {
      return line.getOptionValue(opt);
    }
    return null;
  }

  public static String getValue(CommandType commandType) {
    return getValue(commandType.opt);
  }

  public static String getValueAndCheck(CommandType commandType) throws InvalidParamsException {
    String value = getValue(commandType);
    if (StringUtil.isEmpty(value)) {
      onMissingParameters(commandType.opt);
    }
    return value;
  }

  public static void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("mc", options);
  }

  private static void onMissingParameters(char param) throws InvalidParamsException {
    printHelp();
    throw new InvalidParamsException(StringUtil.formatLog("missing parameter -" + param));
  }

}
