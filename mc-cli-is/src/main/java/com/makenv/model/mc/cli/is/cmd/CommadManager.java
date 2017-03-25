package com.makenv.model.mc.cli.is.cmd;

import com.makenv.model.mc.cli.is.type.ITypeExecutor;
import com.makenv.model.mc.cli.is.type.TypeFactory;
import org.apache.commons.cli.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wgy on 2017/3/22.
 */
public class CommadManager {

    private Logger logger = LoggerFactory.getLogger(CommadManager.class);

    private final Options options = new Options();

    private CommandLine commandLine;

    private String []args;

    public CommadManager(String [] args){

        this.args  = args;

    }


    public void init(){
        //规定可以发送的命令
        for (CommandType cmdType : CommandType.values()) {
            Option input = new Option(String.valueOf(cmdType.opt), cmdType.longOpt, cmdType.hasArg, cmdType.description);
            input.setRequired(cmdType.required);
            options.addOption(input);
        }
        try {
            if (parseAndCheck(args)) {

                if(commandLine.hasOption(CommandType.CMD_TYPE.opt)){

                    String value = commandLine.getOptionValue(CommandType.CMD_TYPE.opt);

                    ITypeExecutor iTypeExecutor = TypeFactory.createTypeExecutor(value,commandLine);

                    iTypeExecutor.execute();

                }

                else {

                    logger.info("not a correct relation");

                    System.exit(1);
                }
            }
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    private boolean parseAndCheck(String[] args) throws Exception {
        if (Arrays.isNullOrEmpty(args)) {
            logger.error("args can not be empty");
            return false;
        }
        CommandLineParser parser = new DefaultParser();
        // parse the command line arguments
        commandLine = parser.parse(options, args);
        for (Option option : options.getOptions()) {
            if (option.isRequired() && !commandLine.hasOption(option.getOpt())) {
                //printHelp();
                logger.error(String.format("parameters %s is required", option.getOpt()));
                return false;
            }
        }
        return true;
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mc-cli-is", options);
    }
}
