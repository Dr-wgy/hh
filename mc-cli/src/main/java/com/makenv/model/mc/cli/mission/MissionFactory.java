package com.makenv.model.mc.cli.mission;

import com.makenv.model.mc.cli.enumeration.TaskTypeEnum;
import com.makenv.model.mc.cli.helper.JedisHelper;

/**
 * Created by wgy on 2017/3/20.
 */
public class MissionFactory {

    public static IMessageResponse organizeAndSendMessage (String mission, JedisHelper jedisHelper,String content){

        TaskTypeEnum taskTypeEnum = TaskTypeEnum.getTask(mission);

        IMessageResponse iMessageResponse = null;

        switch(taskTypeEnum) {

            case UNGRIB_TYPE:

                iMessageResponse = new UngribMessageResponse(jedisHelper,content);

                break;

            case WRF_TYPE:

                iMessageResponse = new WrfMessageResponse(jedisHelper,content);

                break;



        }

        return iMessageResponse;
    }

}
