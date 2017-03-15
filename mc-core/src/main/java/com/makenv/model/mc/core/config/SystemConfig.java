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
  private CshShell csh;
  private Model model;
  private String instance;
  private Renv renv;

  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public Renv getRenv() {
    return renv;
  }

  public void setRenv(Renv renv) {
    this.renv = renv;
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public Pbs getPbs() {
    return pbs;
  }

  public void setPbs(Pbs pbs) {
    this.pbs = pbs;
  }

  public CshShell getCsh() {
    return csh;
  }

  public void setCsh(CshShell csh) {
    this.csh = csh;
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

  public static class Renv {
    private String sys;

    public String getSys() {
      return sys;
    }

    public void setSys(String sys) {
      this.sys = sys;
    }
  }
}
