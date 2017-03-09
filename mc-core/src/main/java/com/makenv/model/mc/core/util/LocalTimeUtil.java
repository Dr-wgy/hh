package com.makenv.model.mc.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by alei on 2017/2/28.
 */
public class LocalTimeUtil {
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String YMD_DATE_FORMAT = "yyyyMMdd";

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

  public static LocalDate parse(String date) {
    return parse(date, DEFAULT_DATE_FORMAT);
  }

  public static LocalDate parse(String date, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return LocalDate.parse(date, formatter);
  }

  public static long between(LocalDate date1, LocalDate date2) {
    return Math.abs(ChronoUnit.DAYS.between(date1, date2));
  }
}
