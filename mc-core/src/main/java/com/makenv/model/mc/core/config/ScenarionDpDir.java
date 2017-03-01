package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class ScenarionDpDir {

    private String dirPath;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public DomainDp getDomain() {
        return domain;
    }

    public void setDomain(DomainDp domain) {
        this.domain = domain;
    }

    private DomainDp domain;
}
