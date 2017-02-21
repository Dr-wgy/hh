package com.makenv.model.mc.core.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by alei on 2015/12/14.
 */
public class TimeUtil {
  private final static String DATE_FORMAT = "yyyy-MM-dd";
  private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static Date phraseDate(String dateString) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    return sdf.parse(dateString);
  }

  public static Date phraseTime(String dateString) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
    return sdf.parse(dateString);
  }

  public static Date phrase(String dateString, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.parse(dateString);
  }

  public static Date phraseWithUTC(String dateString, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.parse(dateString);
  }

  public static String formatDate(Date date) {
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    return format.format(date);
  }

  public static String formatTime(Date date) {
    SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
    return format.format(date);
  }

  public static String formatDate(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String format(Date time, String format, String timeZone) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
    return sdf.format(time);
  }

  public static String format(TemporalAccessor date, String format, String timeZone) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(timeZone));
    return dateTimeFormatter.format(date);
  }

  public static String format(TemporalAccessor date, String format) {
    return format(date, format, ZoneId.systemDefault().getId());
  }

  /**
   * @param days 2016001
   * @return 2015-01-01
   */
  public static String daysToTime(String days) {
    String _year = days.substring(0, 4);
    String _days = days.substring(4, 7);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, Integer.parseInt(_year));
    calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(_days));
    return formatDate(calendar.getTime());
  }

  /**
   * @param time 2015-01-01 [01:01:01]
   * @return 2016001
   */
  public static String timeToDays(Date time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(time);
    return String.format("%s%03d", calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR));
  }

  /**
   * 以日期的形式比较时间大小
   *
   * @param date1
   * @param date2
   * @return
   * @throws ParseException
   */
  public static int compareDate(Date date1, Date date2) throws ParseException {
    if (date1 == null && date2 == null) {
      return 0;
    }
    if (date1 == null) return -1;
    if (date2 == null) return 1;
    Date _date1 = phraseDate(formatDate(date1));
    Date _date2 = phraseDate(formatDate(date2));
    return _date1.compareTo(_date2);
  }

  public static Date getYesterday() {
    return getFixDay(null, -1);
  }

  public static Date getYesterday(Date date) {
    return getFixDay(date, -1);
  }

  /**
   * 获取明天日期
   *
   * @return
   */
  public static Date getTomorrow() {
    return getFixDay(null, 1);
  }

  /**
   * 获取指定时间的第二天日期
   *
   * @param date
   * @return
   */
  public static Date getTomorrow(Date date) {
    return getFixDay(date, 1);
  }

  public static Date getFixDay(Date date, int days) {
    Calendar calendar = Calendar.getInstance();
    if (date != null) calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    if (days != 0) calendar.add(Calendar.DAY_OF_YEAR, days);
    return calendar.getTime();
  }

  public static Date getToday(Date date) {
    return getFixDay(date, 0);
  }

  public static Date getToday() {
    return getFixDay(null, 0);
  }

  public static boolean isSameDay(Date date1, Date date2) {
    return DateUtils.isSameDay(date1, date2);
  }

  public static LocalDateTime convertDateToLocaleDateTime(Date date){

    if(date != null) {

      return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    return  null;
  }

  public static Date convertLocaleDateTimeToDate(LocalDateTime localDateTime) {

    if(localDateTime != null) {

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    return null;
  }
}
