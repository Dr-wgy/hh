package com.makenv.model.mc.message.handler;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgy on 2017/2/22.
 */
public class HandlerChain implements Handler {

    private List<Handler> handlerList = new ArrayList<Handler>();

    private int currIndex = 0;

    public HandlerChain addHandler(Handler handler){

        this.handlerList.add(handler);

        return this;
    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

            if(currIndex  == handlerList.size()) {

                return;
            }

            else {

                handlerList.get(currIndex++).doHandler(handlerChain);

            }
    }
}
