package com.makenv.model.mc.cli.mission;

import com.makenv.model.mc.cli.bean.Message;
import com.makenv.model.mc.cli.helper.JedisHelper;
import com.makenv.model.mc.cli.parser.RegexKeyValueParser;
import com.makenv.model.mc.core.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wgy on 2017/3/20.
 */
public class UngribMessageResponse extends AbstractMessgaeResponse {

    private Logger logger = LoggerFactory.getLogger(UngribMessageResponse.class);

    private JedisHelper jedisHelper;

    private String  content;

    public UngribMessageResponse(JedisHelper jedisHelper,String content) {

        this.jedisHelper = jedisHelper;

        this.content = content;
    }

    @Override
    public boolean sendMessage() {

        try {

            RegexKeyValueParser regexKeyValueParser = new RegexKeyValueParser();

            regexKeyValueParser.checkParser(content);

            Map map = regexKeyValueParser.parser(content);

            String gfs  = (String)map.get("gfs");

            String[] eachGfses = gfs.split("[\\s]+");

            List gfsStatusList = new ArrayList();

            List gfsInfoList = new ArrayList();

            String fnl  = (String)map.get("fnl");

            List fnlStatusList = new ArrayList();

            List fnlInfoList = new ArrayList();

            String[] eachFnles = fnl.split("[\\s]+");

            Map body = new HashMap();

            splitCode(eachGfses, gfsStatusList, gfsInfoList);

            splitCode(eachFnles, fnlStatusList, fnlInfoList);

            body.put("pathdate",map.get("pathdate"));

            body.put("fnl",fnlStatusList);

            body.put("gfs",gfsStatusList);

            body.put("fnlDesc",fnlInfoList);

            body.put("gfsDesc",gfsInfoList);

            Message message = createMessage(body);

            logger.info(message.getTime().toString());

            jedisHelper.sendMessage(JacksonUtil.objToJson(message));

        } catch (Exception e) {

            logger.error("",e);
        }

        return false;
    }

    private void splitCode(String[] infos, List<String> statusList, List<String> infoList) {

        for( String info : infos ) {

            String[] statusAndInfo = info.split(":");

            if(statusAndInfo.length == 2) {

                statusList.add(statusAndInfo[0]);

                infoList.add(statusAndInfo[1]);

            }
            else {

                statusList.add(statusAndInfo[0]);

                infoList.add("");
            }
        }
    }

    @Override
    public String getType() {

        return "ungrib.result";
    }
}
