package com.makenv.model.mc.core.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by wgy on 2017/3/20.
 */
/*{
        "id": "消息号，每次唯一",
        "time": "消息发送时间，如：2017-02-01 12:00:12",
        "type": "消息类型",
        "body": {//消息体，存储内容数据

        }
        }*/
public class Message {

    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private String type;
    private Object body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
