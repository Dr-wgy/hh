package com.makenv.model.mc.core.util;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alei on 2015/12/11.
 */
public class StringUtil {

  public static String md5(String str) {
    return Hashing.md5().hashString(str, Charsets.UTF_8).toString();
  }

  public static boolean isEmpty(String str) {
    return Strings.isNullOrEmpty(str);
  }

  public static boolean hasEmpty(String... strArr) {
    for (String str : strArr) {
      if (isEmpty(str)) {
        return true;
      }
    }
    return false;
  }

  public static String format(String separator, Object... params) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Object obj : params) {
      if (i++ > 0) sb.append(separator);
      sb.append(obj);
    }
    return sb.toString();
  }

  public static String formatLog(String desc, Object... params) {
    StringBuilder sb = new StringBuilder();
    sb.append(desc);
    sb.append(" - [");
    int i = 0;
    for (Object obj : params) {
      if (i++ > 0) sb.append(",");
      sb.append(obj);
    }
    sb.append("]");
    return sb.toString();
  }

  public static LinkedList<Integer> strToIntList(String str) {
    LinkedList<Integer> list = new LinkedList<>();
    String[] arr = str.split(",");
    for (String v : arr) {
      list.add(Integer.parseInt(v));
    }
    return list;
  }

  public static List<String> strToList(String str) {
    return strToList(str, ",");
  }

  public static List<String> strToList(String str, String sep) {
    return Arrays.asList(str.split(sep));
  }
}
