package com.makenv.model.mc.cli.enumeration;

/**
 * Created by wgy on 2017/3/20.
 */
public enum TaskTypeEnum {

    CREATE_DOMAIN_TYPE("create-domain"),

    UNGRIB_TYPE("ungrib"),

    WRF_TYPE("wrf"),

    MCIP_TYPE("mcip"),

    DP_MET_TYPE("dp_met"),

    MEGAN_TYPE("megan"),

    MEIC_TYPE("meic"),

    CAMQ_TYPE("cmaq"),

    DP_CHEM_TYPE("dp_chem");

    public String getTask() {
        return task;
    }

    private String task;

    TaskTypeEnum(String task) {

        this.task = task;
    }

    public static TaskTypeEnum getTask(String mission) {

        TaskTypeEnum taskTypeEnums[] = TaskTypeEnum.values();

        for(TaskTypeEnum taskTypeEnum:taskTypeEnums) {

            if(taskTypeEnum.getTask().equals(mission)) {

                    return taskTypeEnum;
            }
        }

        return null;
    }
}
