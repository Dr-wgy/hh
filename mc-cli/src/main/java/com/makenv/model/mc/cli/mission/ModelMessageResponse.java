package com.makenv.model.mc.cli.mission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.makenv.model.mc.cli.helper.JedisHelper;
import com.makenv.model.mc.cli.parser.RegexKeyValueParser;
import com.makenv.model.mc.core.bean.Message;
import com.makenv.model.mc.core.enu.MessageType;
import com.makenv.model.mc.core.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wgy on 2017/3/20.
 */
public class WrfMessageResponse  extends AbstractMessgaeResponse {

    private Logger logger = LoggerFactory.getLogger(WrfMessageResponse.class);

    private String content;

    private JedisHelper jedisHelper;

    public WrfMessageResponse(JedisHelper jedisHelper, String content) {

        this.content = content;

        this.jedisHelper = jedisHelper;
    }

    @Override
    public boolean sendMessage() {

        RegexKeyValueParser regexKeyValueParser = new RegexKeyValueParser();

        regexKeyValueParser.checkParser(content);

        Map map = regexKeyValueParser.parser(content);

        Message message = createMessage(map);

        try {

            jedisHelper.sendMessage(JacksonUtil.objToJson(message));

        } catch (JsonProcessingException e) {

            logger.error("",e);

           //e.printStackTrace();

            return false;

        }

        return true;
    }

    @Override
    public String getType() {

        return MessageType.MB_START_MODEL_RESULT.id;
    }
}
