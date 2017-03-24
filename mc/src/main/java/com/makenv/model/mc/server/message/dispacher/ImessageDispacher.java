package com.makenv.model.mc.server.message.dispacher;

import com.makenv.model.mc.core.bean.Message;

/**
 * Created by wgy on 2017/2/20.
 */
public interface ImessageDispacher {

    boolean dispacher(Message message);
}
