package com.makenv.model.mc.server.message.pojo;

/**
 * Created by wgy on 2017/2/23.
 */
public class Emis {

  private String calctype;
  private String psal;
  private String ssal;
  //  private List<String> model;
//  private Cmaq cmaq;
//  private Megan megan;
  private String sourceid;
  private String meiccityconfig;

  public String getMeiccityconfig() {
    return meiccityconfig;
  }

  public void setMeiccityconfig(String meiccityconfig) {
    this.meiccityconfig = meiccityconfig;
  }

  public String getSourceid() {
    return sourceid;
  }

  public void setSourceid(String sourceid) {
    this.sourceid = sourceid;
  }

  public String getPsal() {
    return psal;
  }

  public void setPsal(String psal) {
    this.psal = psal;
  }

  public String getSsal() {
    return ssal;
  }

  public void setSsal(String ssal) {
    this.ssal = ssal;
  }

  public String getCalctype() {
    return calctype;
  }

  public void setCalctype(String calctype) {
    this.calctype = calctype;
  }


}
