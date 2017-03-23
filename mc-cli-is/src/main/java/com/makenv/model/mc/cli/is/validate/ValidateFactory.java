package com.makenv.model.mc.cli.is.validate;

import com.makenv.model.mc.cli.is.enumeration.TaskTypeEnum;
import com.makenv.model.mc.core.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;

/**
 * Created by wgy on 2017/3/22.
 */
public class ValidateFactory {

    public static TaskValidator createValidator(TaskTypeEnum taskTypeEnum, Workbook workbook, String file){

        return new TaskValidator(workbook.getSheet(taskTypeEnum.getTask()),file);

    }
}
