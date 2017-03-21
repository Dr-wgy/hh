package com.makenv.model.mc.cli.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wgy on 2017/3/20. eg path=2016-02-03&fnl=0&gfs=&fnlDesc=&gfsDesc=
 */
public class RegexKeyValueParser {

    private static final String name = "name";

    private static final String value = "value";

    private static String matcherGroupRegex =  "(?<"+ RegexKeyValueParser.name +">[\\w]+)=(?<" + RegexKeyValueParser.value + ">[\\w\\,\\s\\-\\n\\:]*)";

    private static String macherRegex = "([\\w]+=[\\w\\,\\s\\-\\n\\:]*[&]*)*";

    public boolean checkParser(String content){

        return macherRegex.matches(content);
    }

    public Map<String,String> parser(String content){

        Map<String,String> map = new HashMap();

        Pattern pattern = Pattern.compile(matcherGroupRegex);

        Matcher matcher = pattern.matcher(content);

        while(matcher.find()) {

            map.put(matcher.group("name"),matcher.group("value"));

        }

        return map;
    }
}
