package com.makenv.model.mc.meic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makenv.model.mc.meic.response.MeicRunResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wgy on 2017/3/12.
 */
public class MeicRunRequest extends AnstractRequest {

    private ContentType contentType = ContentType.TEXT_PLAIN;

    protected final static String ENCODING = "utf-8";
    private File paramsFile;
    private Logger logger = LoggerFactory.getLogger(MeicRunRequest.class);

    public MeicRunRequest(int timeout, String url, ContentType contentType, String params) {
        super(timeout, url, contentType, params);
    }

    public MeicRunRequest(String url, File params) {
       this.paramsFile = params;
       this.setUrl(url);
    }

    public MeicRunRequest(String url, ContentType contentType, String params) {
        super(url, contentType, params);
    }


    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Content doPost(String param,String url) {
        return doRequest(param, Request.Post(this.getUrl()));
    }

    public MeicRunResponse doPost() {
        return doRequest(Request.Post(this.getUrl()));
    }

    public MeicRunResponse doRequest(Request post) {

        Content content = null;

        try {
            post.addHeader("charset",ENCODING);

            content = post.bodyFile(paramsFile,this.contentType)
                     .socketTimeout(this.getTimeout()).
                     connectTimeout(this.getTimeout()).execute().returnContent();

            if(content != null) {

                String responseBody = content.asString(Charset.forName(ENCODING));

                MeicRunResponse response = objectMapper.readValue(responseBody,MeicRunResponse.class);

                return response;
            }

            else {

                return null;
            }

        } catch (IOException e) {

            e.printStackTrace();

            return null;
        }
    }
}
