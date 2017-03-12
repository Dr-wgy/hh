package com.makenv.model.mc.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wgy on 2016/11/8.
 */
public class FilePathUtil {

    private static final String DELIMITER = "/";


    public static String joinByDelimiter(String delimiter,String ... args){

        if(DELIMITER.equals(delimiter)) {

            return String.join(DELIMITER,args);
        } else {

            List<String> list = new ArrayList<>();

            list.add(delimiter);

            list.addAll(Arrays.asList(args));

            return String.join(DELIMITER,list.toArray(new String[0]));

        }
    }
}
