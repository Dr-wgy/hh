package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class WorkSpacePath {

    private ShareDirPath share;

    private UserDirPath userid;

    public UserDirPath getUserid() {
        return userid;
    }

    public void setUserid(UserDirPath userid) {
        this.userid = userid;
    }

    public ShareDirPath getShare() {
        return share;
    }

    public void setShare(ShareDirPath share) {
        this.share = share;
    }

}
