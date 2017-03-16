package com.makenv.model.mc.meic.constants;

/**
 * Created by wgy on 2017/3/15.
 */
public enum ResultStatus {

    REQUEST_SUCCESS("success"),

    REQUEST_FAIL("fail");

    private String status;

    ResultStatus(String status) {

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
