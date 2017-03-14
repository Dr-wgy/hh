package com.makenv.model.mc.meic.response;

/**
 * Created by wgy on 2017/3/13.
 */
public enum MeicGetStatusEnum {

    RUNNING_STATUS("RUNNING","运行中"),

    KILL_STATUS("KILL","用户终止"),

    DONE_STATUS("DONE","完成"),

    FAIL_STATUS("FAIL","失败");

    public String getDesc() {
        return desc;
    }

    public String getStatus() {
        return status;
    }

    private String desc;

    private String status;

    MeicGetStatusEnum(String status,String desc) {

        this.status = status;

        this.desc = desc;
    }



}
