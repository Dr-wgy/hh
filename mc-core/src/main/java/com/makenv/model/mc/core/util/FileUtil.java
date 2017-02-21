package com.makenv.model.mc.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alei on 2015/12/15.
 */
public class FileUtil {

  public static String readLocalFile(File file) throws IOException {
    FileReader fr = new FileReader(file);
    StringBuilder content = new StringBuilder();
    try (BufferedReader br = new BufferedReader(fr)) {
      String value;
      while ((value = br.readLine()) != null) {
        content.append(value);
        content.append("\r\n");
      }
    }
    return content.toString();
  }

  public static boolean writeLocalFile(File file, String content) throws IOException {
    FileWriter fw = new FileWriter(file);
    try (BufferedWriter bw = new BufferedWriter(fw)) {
      bw.write(content);
    }
    return true;
  }

  public static void writeJson(String outPath, String json) throws IOException {
    FileWriter fw = new FileWriter(new File(outPath));
    fw.write(json);
    fw.close();
  }

  public static boolean save(String path, String content) {

    boolean flag = true;

    File file = new File(path);

    File parentFile = file.getParentFile();

    try {

      if (!parentFile.exists()) {

        parentFile.mkdirs();

      }

      if (!file.exists()) {

        file.createNewFile();
      }

      FileUtil.writeLocalFile(file, content);

    } catch (IOException e) {

      e.printStackTrace();

      flag = false;

    }

    return flag;
  }
}
