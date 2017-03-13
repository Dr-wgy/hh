package com.makenv.model.mc.meic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by wgy on 2017/3/12.
 */
public abstract class AnstractRequest {

    public AnstractRequest() {
    }

    private Logger logger = LoggerFactory.getLogger(AnstractRequest.class);
    private int timeout;
    private String url;
    private ContentType contentType = ContentType.APPLICATION_JSON;
    private String params;

    public static ObjectMapper objectMapper =  new ObjectMapper();

    public int getTimeout() {
        return timeout;
    }

    public AnstractRequest(int timeout, String url, ContentType contentType, String params) {
        this.timeout = timeout;
        this.url = url;
        this.contentType = contentType;
        this.params = params;
    }

    public ObjectMapper getObjectMapper() {

        return objectMapper;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AnstractRequest(String url, String params) {
        this.url = url;
        this.params = params;
    }

    public AnstractRequest(String url, ContentType contentType, String params) {
        this.url = url;
        this.contentType = contentType;
        this.params = params;
    }

    public Content doPost(String param, String path) {
        return doRequest(param, Request.Post(path));
    }

    public Content doGet(String param, String path) {
        path = param == null ? path : (path + "?" + param);
        return doRequest(null, Request.Get(path));
    }

    protected Content doRequest(String param, Request request) {
        Content content = null;
        Exception exception = null;
        try {
            if (param != null) {
                request = request.bodyString(param, contentType);
            }

            content = request
                    .socketTimeout(timeout)
                    .connectTimeout(timeout)
                    .execute()
                    .returnContent();


        } catch (Exception e) {

           logger.info("reqeustFailed",e);

        }

        return content;
    }

}
