package com.makenv.model.mc.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by alei on 2017/2/28.
 */
public class LocalTimeUtil {
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

  public static String format(LocalDate date, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return date.format(formatter);
  }

  public static String format(LocalDate date) {
    return format(date, DEFAULT_DATE_FORMAT);
  }

  public static String formatToday(String format) {
    return format(LocalDate.now(), format);
  }

  public static String formatToday() {
    return format(LocalDate.now());
  }
}