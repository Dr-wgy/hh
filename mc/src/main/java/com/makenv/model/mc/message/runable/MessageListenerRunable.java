package com.makenv.model.mc.message.runable;


import com.makenv.model.mc.constant.Constants;
import com.makenv.model.mc.message.body.Message;
import com.makenv.model.mc.message.dispacher.ImessageDispacher;
import com.makenv.model.mc.message.redis.RedisService;


/**
 * Created by wgy on 2017/2/20.
 */
public class MessageListenerRunable implements Runnable {

    private ImessageDispacher messageDispacher;

    private RedisService redisService;

    private Message message;

    public MessageListenerRunable(Message message, ImessageDispacher messageDispacher, RedisService redisService) {
        this.messageDispacher = messageDispacher;
        this.message = message;
        this.redisService = redisService;
    }

    public void setMessageDispacher(ImessageDispacher messageDispacher) {
        this.messageDispacher = messageDispacher;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


    @Override
    public void run() {

        boolean flag = messageDispacher.dispacher(message);

        if(flag) {

            //成功之后从队列中移除
            redisService.rpop(String.join(":", Constants.TEMP_QUEUE_NAME_PREFIX,message.getId()));
        }

    }
}
