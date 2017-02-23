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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

  public static boolean writeAppendLocalFile(File file, String content) throws IOException {
    FileWriter fw = new FileWriter(file,true);
    try (BufferedWriter bw = new BufferedWriter(fw)) {
      content = content + "\r\n";
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

  public static void writeLogByDaily(String fileNamePrefix,String content) {

    File file = new File(String.join("-",fileNamePrefix,LocalDate.now().format(DateTimeFormatter.ISO_DATE)));

    try {

      writeAppendLocalFile(file,content);

    } catch (IOException e) {

      e.printStackTrace();
    }

  }

  /**
   * cp file
   *
   * @param source
   * @param dest
   * @throws IOException
   */
  public static void copyFile(String source, String dest)
          throws IOException {
    Path sourcePath = Paths.get(source);
    Path destPath = Paths.get(dest);
    File file = new File(dest);
    if (!file.exists() || file.getParentFile() != null) {
      file.getParentFile().mkdirs();
    }
    Files.copy(sourcePath, destPath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * mv file
   *
   * @param source
   * @param dest
   * @throws IOException
   */
  public static void moveFile(String source, String dest)
          throws IOException {
    Path sourcePath = Paths.get(source);
    Path destPath = Paths.get(dest);
    File file = new File(dest);
    if (!file.exists() || file.getParentFile() != null) {
      file.getParentFile().mkdirs();
    }
    Files.move(sourcePath, destPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
  }

}
