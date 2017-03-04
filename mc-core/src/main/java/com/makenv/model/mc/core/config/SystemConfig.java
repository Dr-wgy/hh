package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class SystemConfig {

  private RootConfigPath root;
  private WorkSpacePath workspace;
  private SyncPath sync;
  private TemplatePath template;
  private Pbs pbs;

  public Pbs getPbs() {
    return pbs;
  }

  public void setPbs(Pbs pbs) {
    this.pbs = pbs;
  }

  public RootConfigPath getRoot() {
    return root;
  }

  public void setRoot(RootConfigPath root) {
    this.root = root;
  }

  public TemplatePath getTemplate() {
    return template;
  }

  public void setTemplate(TemplatePath template) {
    this.template = template;
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
