package com.makenv.model.mc.cli.is.enumeration;

/**
 * Created by wgy on 2017/3/23.
 */
public enum TypeEnum {

    MISSION_TYPE("task");

    public String getType() {
        return type;
    }

    private String type ;

    TypeEnum(String type){

        this.type = type;

    }

    public static TypeEnum getType(String type) {

        TypeEnum typeEnums[] = TypeEnum.values();

        for(TypeEnum typeEnum:typeEnums) {

            if(typeEnum.getType().equals(type)) {

                return typeEnum;
            }
        }

        return null;
    }
}
