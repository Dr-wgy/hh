package com.makenv.model.mc.cli.is.type;

import com.makenv.model.mc.cli.is.enumeration.TypeEnum;
import redis.clients.jedis.Protocol;
import org.apache.commons.cli.CommandLine;

/**
 * Created by wgy on 2017/3/23.
 */
public class TypeFactory {

    public static ITypeExecutor createTypeExecutor(String type, CommandLine  commandLine){

        TypeEnum typeEnum = TypeEnum.getType(type);

        switch (typeEnum) {

            case MISSION_TYPE:

                return new MissionTypeExecutor(commandLine);

        }

        return null;
    }
}
