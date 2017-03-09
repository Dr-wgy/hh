package com.makenv.model.mc.server.message.service;

import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.DomainCreateBean;

/**
 * Created by wgy on 2017/2/23.
 */
public interface ModelService {

  boolean startModelTask(ModelStartBean modelStartBean);

  boolean doCreateBean(DomainCreateBean domainCreateBean);
}
