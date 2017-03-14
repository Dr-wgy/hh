package com.makenv.model.mc.meic.constants;

/**
 * Created by wgy on 2017/3/13.
 */
public enum MeicType {

    MEICTYPE_SERVER("server","服务"),

    MEICTYPE_CACHE("cache","缓存");

    private String type;

    private String desc;

    MeicType(String type,String desc){

        this.type = type;

        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static MeicType getMeiType (String type) {

       MeicType[] meicTypes = MeicType.values();

       for(MeicType meicType:meicTypes) {

           if(meicType.getType().equals(type)) {

                return meicType;
           }
       }

       return null;

    }
}
