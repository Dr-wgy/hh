package com.makenv.model.mc.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

  public static LocalDateTime parseTime(String time, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return LocalDateTime.parse(time, formatter);
  }

  public static String ldToUcTime(String ltDate, int startHour) {
    LocalDate date = parse(ltDate, YMD_DATE_FORMAT);
    ZonedDateTime utcZoned = date.atStartOfDay().atZone(ZoneId.of("UTC"));
    return format(utcZoned.toLocalDate(), YMD_DATE_FORMAT) + String.format("%02d", startHour);
  }
}
