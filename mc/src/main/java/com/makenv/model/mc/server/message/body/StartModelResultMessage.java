package com.makenv.model.mc.server.message.body;

/**
 * Created by alei on 2017/3/23.
 */
public class StartModelResultMessage {
  private String scenarioid;
  private int index;
  private String date;
  private int code;
  private String desc;

  public String getScenarioid() {
    return scenarioid;
  }

  public void setScenarioid(String scenarioid) {
    this.scenarioid = scenarioid;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
