package com.makenv.model.mc.message.handler;

/**
 * Created by wgy on 2017/2/23.
 */
public class GeneateGriddescHandler implements Handler {

    //生成geiddesc
    public  boolean generateGriddesc(){

        return false;
    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        boolean flag = generateGriddesc();

        if(flag) {

            handlerChain.addHandler(handlerChain);
        }

    }
}
