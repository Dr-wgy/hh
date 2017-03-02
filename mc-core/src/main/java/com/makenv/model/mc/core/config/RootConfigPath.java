package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class RootConfigPath {

  private String workspace;
  private String sync;
  private String script;
  private String wrf;

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public String getWrf() {
    return wrf;
  }

  public void setWrf(String wrf) {
    this.wrf = wrf;
  }

  public String getWorkspace() {
    return workspace;
  }

  public void setWorkspace(String workspace) {
    this.workspace = workspace;
  }

  public String getSync() {
    return sync;
  }

  public void setSync(String sync) {
    this.sync = sync;
  }
}
