package com.makenv.model.mc.core.constant;

import com.makenv.model.mc.core.annotation.ErrorDescription;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2017/3/24.
 */
public class ResponseConstant {
  public final static int SUC_CODE = 0;
  @ErrorDescription("system exception")
  public final static int ERR_SYS = 9999;
  @ErrorDescription("params invalid")
  public final static int ERR_PARAMS = 1001;
  @ErrorDescription("commit job failed")
  public final static int ERR_COMMIT_JOB = 1002;


  private final static Map<Integer, String> ERROR_INFO = new HashMap<>();

  public static String getDescription(int code) {
    return ERROR_INFO.containsKey(code) ? ERROR_INFO.get(code) : "";
  }

  static {
    Field[] fields = ResponseConstant.class.getDeclaredFields();
    try {
      for (Field field : fields) {
        if (field.isAnnotationPresent(ErrorDescription.class)) {
          ErrorDescription errorDescription = field.getDeclaredAnnotation(ErrorDescription.class);
          int value = (int) field.get(null);
          ERROR_INFO.put(value, errorDescription.value());
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
