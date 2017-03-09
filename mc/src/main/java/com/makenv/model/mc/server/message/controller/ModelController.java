package com.makenv.model.mc.server.message.controller;

import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.annoation.MessageInvoker;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;
import com.makenv.model.mc.server.message.pojo.ModelContinueBean;
import com.makenv.model.mc.server.message.pojo.VideoConvertBean;
import com.makenv.model.mc.server.message.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Created by wgy on 2017/2/21.
 */
@Controller
@MessageInvoker
public class ModelController {

    @Autowired
    private ModelService modelService;
    private Logger logger = LoggerFactory.getLogger(ModelController.class);

    @MessageInvoker("model.start")
    public boolean modelStart(ModelStartBean modelStartBean) throws IOException {
        modelService.startModelTask(modelStartBean);
        logger.info("model.start");
        System.out.println("model.start");
        return true;
    }

    @MessageInvoker("model.continue")
    public boolean modeContinue(ModelContinueBean modelContinueBean){

        logger.info("model.continue");

        System.out.println("model.continue");

        return true;
    }

    @MessageInvoker("video.convert")
    public boolean videoConvert(VideoConvertBean videoConvertBean){

        logger.info("video.convert");

        System.out.println("video.convert");

        return true;
    }

    @MessageInvoker("model.stop")
    public boolean modelStop(ModelStartBean modelStartBean){

        logger.info("model.stop");

        System.out.println("model.stop");

        return true;
    }

    @MessageInvoker("domain.create")
    public boolean domainCreate(DomainCreateBean domainCreateBean){

        logger.info("domain.create");

        System.out.println("domain.create");

        return modelService.doCreateBean(domainCreateBean);
    }

}
