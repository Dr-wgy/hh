package com.makenv.model.mc.server.message.body;

/**
 * Created by wgy on 2017/2/21.
 */
public class MessageWrapper {

    public MessageWrapper(String message, String tempQueueName) {
        this.message = message;
        this.tempQueueName = tempQueueName;
    }

    public MessageWrapper() {

    }

    private String message;

    private String tempQueueName;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTempQueueName() {
        return tempQueueName;
    }

    public void setTempQueueName(String tempQueueName) {
        this.tempQueueName = tempQueueName;
    }


}
