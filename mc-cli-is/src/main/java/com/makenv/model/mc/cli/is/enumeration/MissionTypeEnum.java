package com.makenv.model.mc.cli.is.enumeration;

/**
 * Created by wgy on 2017/3/20.
 */
public enum MissionTypeEnum {

    GEOGRID("geogrid"),

    UNGRIB_TYPE("ungrib"),

    WRF_TYPE("wrf"),

    MCIP_TYPE("mcip"),

    MEGAN_TYPE("megan"),

    MEIC_TYPE("meic"),

    ICON_TYPE("icon"),

    BCON_TYPE("bcon");

    public String getTask() {
        return mission;
    }

    private String mission ;

    MissionTypeEnum(String mission) {

        this.mission = mission;
    }

    public static MissionTypeEnum getMission(String mission) {

        MissionTypeEnum missionTypeEnums[] = MissionTypeEnum.values();

        for(MissionTypeEnum missionTypeEnum:missionTypeEnums) {

            if(missionTypeEnum.getTask().equals(mission)) {

                    return missionTypeEnum;
            }
        }

        return null;
    }
}
