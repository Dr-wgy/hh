package com.makenv.model.mc.server.message.pojo;

import java.util.List;

/**
 * Created by wgy on 2017/2/23.
 */
public class Emis {

  private String calctype;
  private String actionlist;
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


  public String getActionlist() {
    return actionlist;
  }

  public void setActionlist(String actionlist) {
    this.actionlist = actionlist;
  }

  public String getCalctype() {
    return calctype;
  }

  public void setCalctype(String calctype) {
    this.calctype = calctype;
  }


}
