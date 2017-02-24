package com.makenv.model.mc.message.task;

import com.makenv.model.mc.message.handler.HandlerChain;
import com.makenv.model.mc.message.pojo.ModelStartBean;

/**
 * Created by wgy on 2017/2/23.
 */
public abstract class ModelTask {

     ModelTask(ModelStartBean modelStartBean) {
        this.modelStartBean = modelStartBean;
    }

    ModelTask() {
    }

    public ModelStartBean getModelStartBean() {
        return modelStartBean;
    }

    public void setModelStartBean(ModelStartBean modelStartBean) {
        this.modelStartBean = modelStartBean;
    }

    private ModelStartBean modelStartBean;

    public abstract void doModelTask();

    protected HandlerChain buildHanlderChain(){

        return null;
    }
}
