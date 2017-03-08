package com.makenv.model.mc.message.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by alei on 2017/3/8.
 */
public class McUtil {
  public static boolean needReInitial(LocalDate baseDate, LocalDate compDate) {
    long days = ChronoUnit.DAYS.between(baseDate, compDate);
    return days % 5 == 1;
  }

  public static boolean needReInitial(String baseDate, String compDate) {
    LocalDate _baseDate = LocalDate.parse(baseDate);
    LocalDate _compDate = LocalDate.parse(compDate);
    return needReInitial(_baseDate, _compDate);
  }
}
