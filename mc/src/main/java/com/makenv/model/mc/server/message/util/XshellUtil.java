package com.makenv.model.mc.server.message.util;

import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.server.message.controller.ModelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wgy on 2017/3/17.
 */
public class XshellUtil {

    private static  Logger logger = LoggerFactory.getLogger(XshellUtil.class);


    public static boolean checkError(Process process,Validate validate) throws Exception {

        if(process == null) {

            logger.error(process.getClass() + " can't be null object");

            return false;
        }
        String errorInfo = FileUtil.convertInpustreamToString(process.getErrorStream()).trim();

        return validate.validate(errorInfo);
    }

    public static ShellResult executeShell(String cmd,Validate validate) throws Exception {

        Process process = null;

        ShellResult shellResult = new ShellResult();

        try {
            process = Runtime.getRuntime().exec(cmd);

        } catch (IOException e) {

            logger.error("",e);

            shellResult.setExecuteFlag(false);

            return shellResult;
        }

        checkError(shellResult,process,validate);

        return shellResult;
    }

    private static void checkError(ShellResult shellResult,Process process,Validate validate) throws Exception {

        shellResult.setErrorStream(process.getErrorStream());

        String errorInfo = FileUtil.convertInpustreamToString(process.getErrorStream()).trim();

        shellResult.setInfoStream(process.getInputStream());

        shellResult.setErrorput(errorInfo);

        String normalInfo = FileUtil.convertInpustreamToString(process.getErrorStream()).trim();

        shellResult.setOutput(normalInfo);

        shellResult.setExecuteFlag(validate.validate(errorInfo));

    }
}
