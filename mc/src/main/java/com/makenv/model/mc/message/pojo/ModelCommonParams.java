package com.makenv.model.mc.message.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by wgy on 2017/3/3.
 */
public class ModelCommonParams {
  @JsonProperty("datatype")
  private String datatype;//情景类型
  private TimeDate time;
  private boolean initial;

  public boolean isInitial() {
    return initial;
  }

  public void setInitial(boolean initial) {
    this.initial = initial;
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

  private Date pathdate;

  public TimeDate getTime() {
    return time;
  }

  public void setTime(TimeDate time) {
    this.time = time;
  }

  public Date getPathdate() {
    return pathdate;
  }

  public void setPathdate(Date pathdate) {
    this.pathdate = pathdate;
  }
}
