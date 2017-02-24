package com.makenv.model.mc.message.handler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Created by wgy on 2017/2/24.
 */
public class CmdErrorLogProcessor implements Callable<Object> {

    private static final Logger logger = LoggerFactory.getLogger(CmdInputLogProcessor.class);

    private InputStream inputStream;

    public CmdErrorLogProcessor(InputStream inputStream) {

        this.inputStream = inputStream;
    }

    @Override
    public Object call() throws Exception {

        try {
            InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {

                logger.info(line);
            }

        } catch (Exception e) {
            logger.error("", e.getMessage(), e);
        }

        return null;
    }

}
