package com.makenv.model.mc.message.task;

import com.makenv.model.mc.message.handler.HandlerChain;
import com.makenv.model.mc.message.pojo.ModelStartBean;

/**
 * Created by wgy on 2017/2/23.
 * 基准情景
 */
public class BaseModelTask extends ModelTask {

    public BaseModelTask() {
    }

    public BaseModelTask(ModelStartBean modelStartBean) {

        super(modelStartBean);
    }

    @Override
    public void doModelTask() {

        //生成执行链
        HandlerChain handlerChain = buildHanlderChain();

        //执行执行链
        handlerChain.doHandler(handlerChain);

    }

    //根据参数生成执行链

    @Override
    protected HandlerChain buildHanlderChain() {

        HandlerChain handlerChain = new HandlerChain();

        //handlerChain.addHandler();

        ModelStartBean modelStartBean = this.getModelStartBean();


        return handlerChain;
    }
}
