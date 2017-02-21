package com.makenv.model.mc.message.controller;

import com.makenv.model.mc.message.annoation.MessageInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Created by wgy on 2017/2/21.
 */
@Controller
@MessageInvoker
public class ModelController {

    private Logger logger = LoggerFactory.getLogger(ModelController.class);


    @MessageInvoker("model.start")
    public boolean modelStart(String data){

        logger.info("model.start");

        System.out.println("model.start");

        return true;
    }

    @MessageInvoker("model.continue")
    public boolean modeContinue(String data){

        logger.info("model.continue");

        System.out.println("model.continue");

        return true;
    }

    @MessageInvoker("video.convert")
    public boolean videoConvert(String data){

        logger.info("video.convert");

        System.out.println("video.convert");

        return true;
    }

    @MessageInvoker("model.stop")
    public boolean modelStop(String data){

        logger.info("model.stop");

        System.out.println("model.stop");

        return true;
    }
}
