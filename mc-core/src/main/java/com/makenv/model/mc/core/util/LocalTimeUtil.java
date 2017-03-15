package com.makenv.model.mc.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    LocalDateTime time = date.atStartOfDay();
//    ZonedDateTime.of(time,ZoneId.systemDefault());
    ZonedDateTime cttZoned = ZonedDateTime.of(time, ZoneId.of("Asia/Shanghai"));
    ZonedDateTime utcZoned = cttZoned.withZoneSameInstant(ZoneOffset.UTC);
    return format(utcZoned.toLocalDate(), YMD_DATE_FORMAT) + String.format("%02d", startHour);
  }

  public static String zonTimeConvert(String dateStr, ZoneId src, ZoneId target) {
    LocalDate date = parse(dateStr, YMD_DATE_FORMAT);
    LocalDateTime time = date.atStartOfDay();
    ZonedDateTime cttZoned = ZonedDateTime.of(time, src);
    ZonedDateTime utcZoned = cttZoned.withZoneSameInstant(target);
    return format(utcZoned.toLocalDate(), YMD_DATE_FORMAT);

  }

  public static boolean needReInitial(LocalDate baseDate, LocalDate compDate, int reInitialDays) {
    return LocalTimeUtil.between(baseDate, compDate) % reInitialDays == 0;
  }

  public static boolean needReInitial(String baseDate, String compDate, int reInitialDays) {
    LocalDate _baseDate = parse(baseDate, YMD_DATE_FORMAT);
    LocalDate _compDate = parse(compDate, YMD_DATE_FORMAT);
    return needReInitial(_baseDate, _compDate, reInitialDays);
  }

  public static void main(String[] args) {
    int days = Integer.parseInt(args[2]);
    System.out.println(needReInitial(args[0], args[1], days) ? "1" : "0");
  }
}
