package com.makenv.model.mc.core.util;

/**
 * Created by wgy on 2016/11/8.
 */
public class FilePathUtil {

    private static final String DELIMITER = "/";


    public static String joinByDelimiter(String delimiter,String ... args){

        if(StringUtil.isEmpty(delimiter)) {

            delimiter = DELIMITER;
        }

        return String.join(delimiter,args);
    }
}
