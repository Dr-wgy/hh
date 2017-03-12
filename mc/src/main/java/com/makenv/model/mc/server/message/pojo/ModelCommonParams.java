package com.makenv.model.mc.server.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wgy on 2017/3/3.
 */
public class ModelCommonParams {
  @JsonProperty("datatype")
  private String datatype;//情景类型
  private TimeDate time;
  private String pathdate;
  private String simtype;

  public String getSimtype() {
    return simtype;
  }

  public void setSimtype(String simtype) {
    this.simtype = simtype;
  }

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public static class TimeDate {

    private String start;

    private String end;

    public String getStart() {
      return start;
    }

    public void setStart(String start) {
      this.start = start;
    }

    public String getEnd() {
      return end;
    }

    public void setEnd(String end) {
      this.end = end;
    }
  }


  public TimeDate getTime() {
    return time;
  }

  public void setTime(TimeDate time) {
    this.time = time;
  }

  public String getPathdate() {
    return pathdate;
  }

  public void setPathdate(String pathdate) {
    this.pathdate = pathdate;
  }
}
