package com.makenv.model.mc.message.service;

import com.makenv.model.mc.message.pojo.DomainCreateBean;
import com.makenv.model.mc.message.pojo.ModelStartBean;

/**
 * Created by wgy on 2017/2/23.
 */
public interface ModelService {

    void startModelTask(ModelStartBean modelStartBean);

    void doCreateBean(DomainCreateBean domainCreateBean);
}
