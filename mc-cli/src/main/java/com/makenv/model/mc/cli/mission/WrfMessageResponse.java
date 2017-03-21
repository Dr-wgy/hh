package com.makenv.model.mc.cli.mission;

import com.makenv.model.mc.cli.helper.JedisHelper;

/**
 * Created by wgy on 2017/3/20.
 */
public class WrfMessageResponse implements IMessageResponse {

    public WrfMessageResponse(JedisHelper jedisHelper, String content) {

    }

    @Override
    public boolean sendMessage() {
        return false;
    }

    @Override
    public String getType() {

        return "";
    }
}
