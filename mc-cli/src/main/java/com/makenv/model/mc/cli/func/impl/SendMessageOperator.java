package com.makenv.model.mc.cli.func.impl;

import com.makenv.model.mc.cli.cmd.CommandManager;
import com.makenv.model.mc.cli.cmd.CommandType;
import com.makenv.model.mc.cli.func.AbstractOperator;
import com.makenv.model.mc.cli.func.IOperator;
import com.makenv.model.mc.cli.helper.JedisHelper;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by wgy on 2017/3/9.
 */
@Service
public class SendMessageOperator implements IOperator {

    private Logger logger = LoggerFactory.getLogger(UngribOperator.class);

    @Autowired
    private CommandManager commandManager;

    @Autowired
    private JedisHelper jedisHelper;

    @Override
    public String getName() {

        return null;
    }

    @Override
    public void operate() throws Exception {

        String value = commandManager.getValue(CommandType.CMD_DATA.longOpt);

        String pathName = commandManager.getValue(CommandType.CMD_INPUT.longOpt);

        if(StringUtil.isEmpty(value) && StringUtil.isEmpty(pathName)) {

            logger.error("can not send message please add -D or -I paramsters");

            return;

        }

        else if(!StringUtil.isEmpty(value) && !StringUtil.isEmpty(pathName)) {

            commandManager.printHelp();

            logger.info("please read help");

            return;
        }

        else if(!StringUtil.isEmpty(value) && StringUtil.isEmpty(pathName)) {

            jedisHelper.sendMessage(value);

            return;

        }

        else if(!StringUtil.isEmpty(pathName) && StringUtil.isEmpty(value)) {

            try {

                String content = FileUtil.readLocalFile(new File(pathName));

                jedisHelper.sendMessage(content);


            }catch (IOException e) {

                logger.error("the file is not correctly please check the file",e);

            }
        }
    }

    @Override
    public void init() throws Exception {

    }
}
