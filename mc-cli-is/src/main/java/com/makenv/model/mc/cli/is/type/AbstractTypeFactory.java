package com.makenv.model.mc.cli.is.type;

import com.makenv.model.mc.cli.is.cmd.CommandType;
import org.springframework.util.StringUtils;
import org.apache.commons.cli.CommandLine;

/**
 * Created by wgy on 2017/3/23.
 */
public abstract class AbstractTypeFactory implements ITypeExecutor{

    private CommandLine commandLine;

    public AbstractTypeFactory () {


    }


    public AbstractTypeFactory (CommandLine commandLine) {

        this.commandLine = commandLine;
    }


    protected String getValue(String option) {
        if (commandLine.hasOption(option)) {
            return commandLine.getOptionValue(option);
        }
        return null;
    }

    protected String getValue(char opt) {
        if (commandLine.hasOption(opt)) {
            return commandLine.getOptionValue(opt);
        }
        return null;
    }

    protected String getValue(CommandType commandType) {
        return getValue(commandType.opt);
    }

    protected String getValueAndCheck(CommandType commandType) {
        String value = getValue(commandType);
        if (StringUtils.isEmpty(value)) {
            //printHelp();
            throw new RuntimeException();
        }
        return value;
    }
}
