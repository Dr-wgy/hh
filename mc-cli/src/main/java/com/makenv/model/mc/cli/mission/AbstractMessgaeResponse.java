package com.makenv.model.mc.cli.mission;


import com.makenv.model.mc.cli.bean.Message;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wgy on 2017/3/20.
 */
public abstract class AbstractMessgaeResponse implements IMessageResponse {

    public Message createMessage(Object data) {

        Message message = new Message();

        message.setId(UUID.randomUUID().toString());

        message.setTime(new Date());

        message.setBody(data);

        message.setType(getType());

        return message;
    }

    public Object dealMessage(String info){

        return null;
    }
}
