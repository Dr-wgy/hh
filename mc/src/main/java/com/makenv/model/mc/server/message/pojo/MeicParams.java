package com.makenv.model.mc.server.message.pojo;

/**
 * Created by alei on 2017/3/12.
 */
public class MeicParams {
  private Meagen meagen;
  private ForModel model;

  public Meagen getMeagen() {
    return meagen;
  }

  public void setMeagen(Meagen meagen) {
    this.meagen = meagen;
  }

  public ForModel getModel() {
    return model;
  }

  public void setModel(ForModel model) {
    this.model = model;
  }

  public static class Meagen {
    private String shutdown;

    public String getShutdown() {
      return shutdown;
    }

    public void setShutdown(String shutdown) {
      this.shutdown = shutdown;
    }
  }

  public static class ForModel {
    private String name;
    private String submodel;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSubmodel() {
      return submodel;
    }

    public void setSubmodel(String submodel) {
      this.submodel = submodel;
    }
  }
}
