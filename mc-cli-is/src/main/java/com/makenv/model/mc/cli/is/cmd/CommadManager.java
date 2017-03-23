package com.makenv.model.mc.cli.is.cmd;

import com.makenv.model.mc.cli.is.enumeration.TaskTypeEnum;
import com.makenv.model.mc.cli.is.validate.ValidateFactory;
import com.makenv.model.mc.core.util.FileUtil;
import org.apache.commons.cli.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by wgy on 2017/3/22.
 */
public class CommadManager {

    private Logger logger = LoggerFactory.getLogger(CommadManager.class);

    private final Options options = new Options();

    private CommandLine commandLine;

    private String []args;

    private String ruleExcelPath;

    private Workbook  workbook ;

    public CommadManager(String [] args,String ruleExcelPath){

        this.args  = args;

        this.ruleExcelPath = ruleExcelPath;

        File excelFile = new File(ruleExcelPath); //创建文件对象

        FileInputStream is = null; //文件流
        try {

            is = new FileInputStream(excelFile);

            workbook = getWorkbook(is,ruleExcelPath);

        } catch (FileNotFoundException e) {

            logger.error("file is not found",e);

            System.exit(1);

            //e.printStackTrace();
        } catch (IOException e) {

            logger.error("",e);

            System.exit(1);
            //e.printStackTrace();

        }
    }

    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            logger.error("The specified file is not ExcelUtil file");
            System.exit(1);
            //throw new IllegalArgumentException("The specified file is not ExcelUtil file");
        }

        return workbook;
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

                String value = getValue(CommandType.CMD_TYPE);

                //获取文件
                String file = getValue(CommandType.CMD_FILE);

                if(StringUtils.isEmpty(value) || StringUtils.isEmpty(file)) {

                    logger.error("the unkown type");
                    System.exit(1);
                }
                if(!FileUtil.exists(file)){

                    logger.error("file is not exists");
                    System.exit(1);
                }

                TaskTypeEnum taskTypeEnum = TaskTypeEnum.getTask(value);

                ValidateFactory.createValidator(taskTypeEnum,workbook,file).validate();
            }
        } catch (Exception e) {
            logger.error("",e);
            // e.printStackTrace();
        }
    }

    private boolean parseAndCheck(String[] args) throws Exception {
        if (Arrays.isNullOrEmpty(args)) {
            //printHelp();
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

    public String getValue(String option) {
        if (commandLine.hasOption(option)) {
            return commandLine.getOptionValue(option);
        }
        return null;
    }

    String getValue(char opt) {
        if (commandLine.hasOption(opt)) {
            return commandLine.getOptionValue(opt);
        }
        return null;
    }

    String getValue(CommandType commandType) {
        return getValue(commandType.opt);
    }

    public String getValueAndCheck(CommandType commandType) {
        String value = getValue(commandType);
        if (StringUtils.isEmpty(value)) {
            //printHelp();
            throw new RuntimeException();
        }
        return value;
    }



    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("mc-cli", options);
    }
}
