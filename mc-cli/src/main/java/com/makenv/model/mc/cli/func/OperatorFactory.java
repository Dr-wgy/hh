package com.makenv.model.mc.cli.func;

/**
 * Created by alei on 2016/9/6.
 */
public class OperatorFactory {
  public static IOperator getOperator(String type) throws IllegalAccessException, InstantiationException {
    for (FuncType funcType : FuncType.values()) {
      if (funcType.name.equals(type)) {
        return funcType.operator.newInstance();
      }
    }
    return null;
  }
}
