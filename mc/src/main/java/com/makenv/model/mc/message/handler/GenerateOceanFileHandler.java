package com.makenv.model.mc.message.handler;

/**
 * Created by wgy on 2017/2/23.
 */
public class GenerateOceanFileHandler implements Handler {

    private boolean generateOceanFile(){



        return false;
    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        boolean flag = generateOceanFile();

        if(flag) {

            handlerChain.doHandler(handlerChain);

        }
    }
}
