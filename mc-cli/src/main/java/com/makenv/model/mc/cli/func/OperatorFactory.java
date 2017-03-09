package com.makenv.model.mc.cli.func;

import com.makenv.model.mc.cli.func.impl.SendMessageOperator;
import com.makenv.model.mc.cli.func.impl.UngribOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alei on 2016/9/6.
 */
@Component
public class OperatorFactory {
  @Autowired
  private UngribOperator ungribOperator;
  @Autowired
  private SendMessageOperator sendMessageOperator;

  public IOperator getOperator(String type) throws IllegalAccessException, InstantiationException {
    switch (type) {
      case "ungrib":
        return ungribOperator;
      case "send_message":
        return sendMessageOperator;
    }
    return null;
  }
}
