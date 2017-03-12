package com.makenv.model.mc.server.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wgy on 2017/2/23.
 */
public class McipParams {

//    @JsonProperty("X0")
//    private String x0;
//
//    @JsonProperty("Y0")
//    private String y0;

  private int ctm_vert;
  @JsonProperty("CTMLAYS")
  private String ctmlays;

  public int getCtm_vert() {
    return ctm_vert;
  }

  public void setCtm_vert(int ctm_vert) {
    this.ctm_vert = ctm_vert;
  }

  public String getCtmlays() {
    return ctmlays;
  }

  public void setCtmlays(String ctmlays) {
    this.ctmlays = ctmlays;
  }
}
