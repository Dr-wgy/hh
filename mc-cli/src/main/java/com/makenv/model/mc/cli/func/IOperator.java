package com.makenv.model.mc.cli.func;

/**
 * Created by alei on 2016/9/6.
 */
public interface IOperator {
  String getName();

  void operate() throws Exception;

  void init() throws Exception;
}
