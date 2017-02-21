package com.makenv.model.mc.cli.func;

import com.makenv.model.mc.cli.func.impl.UngribOperator;

/**
 * Created by alei on 2016/9/6.
 */
public enum FuncType {
  PREPLAN_FATJSON(1, UngribOperator.class, "ungrib", "execute ungribe action"),;

  public final int index;
  public final Class<? extends IOperator> operator;
  public final String description;
  public final String name;

  FuncType(int index, Class<? extends IOperator> operator, String name, String description) {
    this.index = index;
    this.operator = operator;
    this.description = description;
    this.name = name;
  }
}
