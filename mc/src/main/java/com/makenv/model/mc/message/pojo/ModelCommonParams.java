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

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public static class TimeDate {

    private Date start;

    private Date end;

    public Date getStart() {
      return start;
    }

    public void setStart(Date start) {
      this.start = start;
    }

    public Date getEnd() {
      return end;
    }

    public void setEnd(Date end) {
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
