package com.makenv.model.mc.message.body;

/**
 * Created by wgy on 2017/2/21.
 */
public class MessageWrapper {

    public MessageWrapper(Message message, String tempQueueName) {
        this.message = message;
        this.tempQueueName = tempQueueName;
    }

    public MessageWrapper() {

    }

    private Message message;

    private String tempQueueName;


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTempQueueName() {
        return tempQueueName;
    }

    public void setTempQueueName(String tempQueueName) {
        this.tempQueueName = tempQueueName;
    }


}
