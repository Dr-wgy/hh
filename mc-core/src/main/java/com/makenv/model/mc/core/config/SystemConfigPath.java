package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class SystemConfigPath {

    private RootConfigPath root;

    private WorkSpacePath workspace;

    public SystemConfigPath() {
    }

    private SyncPath sync;

    public RootConfigPath getRoot() {
        return root;
    }

    public void setRoot(RootConfigPath root) {
        this.root = root;
    }

    public SyncPath getSync() {
        return sync;
    }

    public void setSync(SyncPath sync) {
        this.sync = sync;
    }

    public WorkSpacePath getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkSpacePath workspace) {
        this.workspace = workspace;
    }
}
