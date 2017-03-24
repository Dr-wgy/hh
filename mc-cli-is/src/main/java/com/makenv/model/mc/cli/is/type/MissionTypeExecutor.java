package com.makenv.model.mc.cli.is.type;

import com.makenv.model.mc.cli.is.cmd.CommandType;
import com.makenv.model.mc.cli.is.config.RuleConfig;
import com.makenv.model.mc.cli.is.enumeration.Constants;
import com.makenv.model.mc.cli.is.validate.TaskValidator;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.StringUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wgy on 2017/3/23.
 */
public class MissionTypeExecutor extends AbstractTypeFactory {

    private Logger logger = LoggerFactory.getLogger(MissionTypeExecutor.class);

    private RuleConfig ruleConfig;

    private String mission;

    private String file;

    private String version;

    private Sheet sheet;

    public MissionTypeExecutor(CommandLine commandLine) {

        super(commandLine);

        this.ruleConfig = new RuleConfig();

        this.mission = getValue(CommandType.CMD_MISSION.opt);

        this.file = getValue(CommandType.CMD_FILE.opt);

        this.version = getValue(CommandType.CMD_VERSION.opt);

        beforeExecuteCheck(mission,file,version);

        //this.sheet = getWorkbook
    }

    private void beforeExecuteCheck(String mission, String file, String version) {

        if(StringUtil.isEmpty(mission)) {

            logger.error("the mission can not be empty");

            //非正常退出
            System.exit(1);

        }

        if(StringUtil.isEmpty(file)) {

            logger.error("the filePathName can not be empty");

            //非正常退出
            System.exit(1);
        }

        if(!FileUtil.exists(file)) {

            logger.error("the file is not exsits");

            //非正常退出
            System.exit(1);

        }

        if(StringUtil.isEmpty(version)) {

            logger.error("please check your version,the version can not be empty");

            //非正常退出
            System.exit(1);
        }

        String excelDir = ruleConfig.getRuleExcelPath();

        //TODO excel version
        String  excelPath = String.join(".", mission,Constants.EXCEL_SUFFIX_03);

        String  excelFilePath = FilePathUtil.joinByDelimiter(excelDir,excelPath);

        if (!FileUtil.exists(excelFilePath)) {

            logger.error("the excel is not exsits");

            //非正常退出
            System.exit(1);

        }

        try {

            this.sheet = getWorkbook(excelFilePath).getSheet(version);

        } catch (IOException e) {

            logger.error("read excel is failed, please check your excel",e);

            System.exit(1);
        }


    }


    private Workbook getWorkbook(InputStream inputStream, String excelFilePath)
            throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            logger.error("The specified file is not ExcelUtil file");
            System.exit(1);
        }
        return workbook;
    }

    private Workbook getWorkbook(String excelFilePath)
            throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        return getWorkbook(inputStream, excelFilePath);
    }

    @Override
    public void execute() {

        TaskValidator taskValidator = new TaskValidator(sheet,file);

        taskValidator.validate();

    }

}
