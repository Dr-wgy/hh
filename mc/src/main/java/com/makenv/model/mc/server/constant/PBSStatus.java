package com.makenv.model.mc.server.constant;

/**
 * Created by wgy on 2017/3/17.
 */
public enum PBSStatus {

    RUNNING_STATUS("R","job is running"),

    FINISHED_STATUS("C","Job is completed after having run"),

    EXISTS_STATUS("E","Job is exiting after having run - this means that it is not going to run until it is released"),

    QUEUE_STATUS("Q","the job is queued and will run when the resources become available"),

    TRANSFER_STATUS("T","the job is being transferred to a new location - this may happen, e.g., if the node the job had been running on crashed"),

    WAITTING_STATUS("W","the job is waiting - you can submit jobs to run, e.g., after 5PM"),

    SUSPEND_STATUS("S","thie job is suspend");

    private String status;

    private String desc;

    PBSStatus(String status, String desc){

        this.status = status;

        this.desc = desc;

    }

    public static PBSStatus getStatus(String status){

        PBSStatus pbsStatus[] = PBSStatus.values();

        for(PBSStatus eachStatus:pbsStatus)  {

            if(eachStatus.equals(status)) {

                return eachStatus;
            }
        }

        return null;
    }
}
