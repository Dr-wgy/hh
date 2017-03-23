package com.makenv.model.mc.cli.is.enumeration;

import com.makenv.model.mc.cli.is.validate.Validate;

import java.util.function.Function;

/**
 * Created by wgy on 2017/3/22.
 */
public enum RuleEnum {

    SIMPLE_RULES("contains","contain string",(source,pattern)->{return source.contains(pattern);}),

    REGEX_RULES("regex","regex matches",(source,pattern)->{return source.matches(pattern);});

    public String getRuleType() {
        return ruleType;
    }

    private String ruleType;

    private String desc;

    public Validate getValidate() {
        return validate;
    }

    private Validate validate;

    RuleEnum(String ruleType,String desc,Validate validate){

        this.ruleType = ruleType;

        this.desc = desc;

        this.validate = validate;
    }

    public static RuleEnum getRule(String ruleType) {

        RuleEnum RuleEnums[] = RuleEnum.values();

        for(RuleEnum RuleEnum:RuleEnums) {

            if(RuleEnum.getRuleType().equals(ruleType)) {

                return RuleEnum;
            }
        }

        return null;
    }
}
