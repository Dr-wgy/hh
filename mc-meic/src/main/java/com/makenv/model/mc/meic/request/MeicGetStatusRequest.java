package com.makenv.model.mc.meic.request;

import com.makenv.model.mc.meic.response.MeicGetStatusResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicGetStatusRequest extends AnstractRequest {

    public MeicGetStatusRequest() {
    }

    public MeicGetStatusRequest(int timeout, String url, ContentType contentType, String params) {
        super(timeout, url, contentType, params);
    }

    public MeicGetStatusRequest(String url, String params) {
        super(url, params);
    }

    public MeicGetStatusRequest(String url, ContentType contentType, String params) {
        super(url, contentType, params);
    }


    public MeicGetStatusResponse doGetReuest(String param, String path) {
        Content content = super.doGet(param,path);
        MeicGetStatusResponse response = null;
        try {
            response = this.getObjectMapper().readValue(content.asString(),MeicGetStatusResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
