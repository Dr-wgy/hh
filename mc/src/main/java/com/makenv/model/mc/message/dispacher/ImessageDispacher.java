package com.makenv.model.mc.message.dispacher;


import com.makenv.model.mc.message.body.Message;

/**
 * Created by wgy on 2017/2/20.
 */
public interface ImessageDispacher {

    boolean dispacher(Message message);
}
