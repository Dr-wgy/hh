package com.makenv.model.mc.message.util;

import com.makenv.model.mc.core.util.LocalTimeUtil;

import java.time.LocalDate;

/**
 * Created by alei on 2017/3/8.
 */
public class McUtil {
  public static boolean needReInitial(LocalDate baseDate, LocalDate compDate, int reInitialDays) {
    return LocalTimeUtil.between(baseDate, compDate) % reInitialDays == 1;
  }

  public static boolean needReInitial(String baseDate, String compDate, int reInitialDays) {
    LocalDate _baseDate = LocalDate.parse(baseDate);
    LocalDate _compDate = LocalDate.parse(compDate);
    return needReInitial(_baseDate, _compDate, reInitialDays);
  }
}
