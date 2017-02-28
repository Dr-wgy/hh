package com.makenv.model.mc.cli.cmd;

/**
 * Created by alei on 2016/9/7.
 */
public enum CommandType {
  CMD_TYPE(true, 't', "type", true, "function type"),
  CMD_OUTPUT(false, 'o', "output", true, "output file"),
  CMD_INPUT(false, 'i', "input", true, "input file path"),
  CMD_DATE(false, 'd', "date", true, "date time");

  public final boolean required;
  public final char opt;
  public final String longOpt;
  public final boolean hasArg;
  public final String description;

  CommandType(boolean required, char opt, String longOpt, boolean hasArg, String description) {
    this.required = required;
    this.opt = opt;
    this.longOpt = longOpt;
    this.hasArg = hasArg;
    this.description = description;
  }
}
