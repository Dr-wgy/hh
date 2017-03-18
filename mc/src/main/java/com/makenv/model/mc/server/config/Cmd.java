package com.makenv.model.mc.server.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by wgy on 2017/3/17.
 */
@Configuration
@ApplicationProperties(locations = "classpath:cmd.yml")
public class Cmd {

    public String getQstat() {
        return qstat;
    }

    public void setQstat(String qstat) {
        this.qstat = qstat;
    }

    private String qstat;
}
