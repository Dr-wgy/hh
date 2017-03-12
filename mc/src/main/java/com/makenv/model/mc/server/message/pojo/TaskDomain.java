package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class TaskDomain {

  private CommonParams common; //公共参数
  private CmaqParams cmaq; //cmaq 参数
  private WrfParams wrf; // wrf 参数
  private McipParams mcip; // mcip 参数
  private MeicParams meic;

  public MeicParams getMeic() {
    return meic;
  }

  public void setMeic(MeicParams meic) {
    this.meic = meic;
  }

  public CommonParams getCommon() {
    return common;
  }

  public void setCommon(CommonParams common) {
    this.common = common;
  }

  public WrfParams getWrf() {
    return wrf;
  }

  public void setWrf(WrfParams wrf) {
    this.wrf = wrf;
  }

  public McipParams getMcip() {
    return mcip;
  }

  public void setMcip(McipParams mcip) {
    this.mcip = mcip;
  }

  public CmaqParams getCmaq() {
    return cmaq;
  }

  public void setCmaq(CmaqParams cmaq) {
    this.cmaq = cmaq;
  }
}
