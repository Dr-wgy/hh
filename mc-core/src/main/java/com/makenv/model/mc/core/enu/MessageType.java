package com.makenv.model.mc.core.enu;

/**
 * Created by alei on 2017/3/24.
 */
public enum MessageType {
  MB_CREATE_DOMAIN_RESULT("domain.create.result"),
  MB_START_MODEL_RESULT("model.start.result"),
  MB_STOP_MODEL_RESULT("model.stop.result"),
  MB_UNGRIB_RESULT("ungrib.result"),
  BM_START_MODEL("model.start"),
  BM_STOP_MODEL("model.stop"),
  BM_CREATE_DOMAIN("domain.create");

  public final String id;

  MessageType(String id) {
    this.id = id;
  }
}
