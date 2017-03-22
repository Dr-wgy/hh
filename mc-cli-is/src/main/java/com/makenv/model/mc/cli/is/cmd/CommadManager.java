package com.makenv.model.mc.cli_is.cmd;

import org.apache.commons.cli.*;
import org.assertj.core.util.Arrays;

/**
 * Created by wgy on 2017/3/22.
 */
public class CommadManager {

    private final Options options = new Options();

    private CommandLine commandLine;

    public CommadManager(String [] args){

        init(args);
    }

    private void init(String[] args){
        //规定可以发送的命令
        for (CommandType cmdType : CommandType.values()) {
            Option input = new Option(String.valueOf(cmdType.opt), cmdType.longOpt, cmdType.hasArg, cmdType.description);
            input.setRequired(cmdType.required);
            options.addOption(input);
        }

       /* if (parseAndCheck(args.getSourceArgs())) {
            operatorFactory.getOperator(getValue(CommandType.CMD_TYPE)).operate();
        }*/

    }

    private boolean parseAndCheck(String[] args) throws Exception {
        if (Arrays.isNullOrEmpty(args)) {
            printHelp();
            return false;
        }
        CommandLineParser parser = new DefaultParser();
        // parse the command line arguments
        commandLine = parser.parse(options, args);
        for (Option option : options.getOptions()) {
            if (option.isRequired() && !commandLine.hasOption(option.getOpt())) {
                printHelp();
                logger.error(String.format("parameters %s is required", option.getOpt()));
                return false;
            }
        }
        return true;
    }


    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mc-cli", options);
    }
}
