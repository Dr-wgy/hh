package com.makenv.model.mc.core.util;

import java.io.*;
import java.nio.charset.Charset;
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
    FileWriter fw = new FileWriter(file, true);
    try (BufferedWriter bw = new BufferedWriter(fw)) {
      content = content + "\r\n";
      bw.write(content);
    }
    return true;
  }

  public static boolean writeAppendLocalFileInLinux(File file, String content) throws IOException {
    FileWriter fw = new FileWriter(file, true);
    try (BufferedWriter bw = new BufferedWriter(fw)) {
      content = content + "\n";
      bw.write(content);
    }
    return true;
  }

  public static void writeJson(String outPath, String json) throws IOException {
    FileWriter fw = new FileWriter(new File(outPath));
    fw.write(json);
    fw.close();
  }

  public static boolean checkAndMkdir(String path) {
    File dir = new File(path);
    if (!dir.exists() || !dir.isDirectory()) {
      return dir.mkdirs();
    }
    return true;
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

  public static void writeLogByDaily(String fileNamePrefix, String content) {

    File file = new File(String.join("-", fileNamePrefix, LocalDate.now().format(DateTimeFormatter.ISO_DATE)));

    try {

      if(!file.exists()) {

       FileUtil.createFile(file);
      }

      writeAppendLocalFile(file, content);

    } catch (IOException e) {

      e.printStackTrace();
    }

  }

  public static void createFile(File file) {
    File parentFile = file.getParentFile();
    if(parentFile != null && !parentFile.exists()) {
      parentFile.mkdirs();
    }
    else {
      if(!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public static void createFile(String fileName) {
    createFile(new File(fileName));
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

  public static void copyFolder(File src, File dest) throws IOException {
    if (src.isDirectory()) {
      if (!dest.exists()) {
        dest.mkdirs();
      }
      String files[] = src.list();
      for (String file : files) {
        File srcFile = new File(src, file);
        File destFile = new File(dest, file);
        copyFolder(srcFile, destFile);
      }
    } else {
      InputStream in = new FileInputStream(src);
      OutputStream out = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = in.read(buffer)) > 0) {
        out.write(buffer, 0, length);
      }
      in.close();
      out.close();
    }
  }

  /**
   * create symbolic link
   *
   * @param existFile 已存在的文件
   * @param newLink   软连接后的新文件
   */
  public static boolean symbolicLink(String existFile, String newLink) {
    Path existingFile = Paths.get(existFile);
    Path link = Paths.get(newLink);
    File f = link.toFile();
    //存在的情况下 删除了 再新建啊！
    if (Files.isSymbolicLink(link)) {
      if (!f.delete()) return false;
      try {
        Files.createSymbolicLink(link, existingFile);
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    if (f.exists()) {
      return false;
    }
    try {
      Files.createSymbolicLink(link, existingFile);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static String readLastLine(File file, String charset) {
    if (!file.exists() || file.isDirectory() || !file.canRead()) {
      return null;
    }
    RandomAccessFile randomaccessfile = null;
    try {
      randomaccessfile = new RandomAccessFile(file, "r");
      long len = randomaccessfile.length();
      if (len == 0L) {
        return "";
      } else {
        long pos = len - 1;
        while (pos > 0) {
          pos--;
          randomaccessfile.seek(pos);
          if (randomaccessfile.readByte() == '\n') {
            break;
          }
        }
        if (pos == 0) {
          randomaccessfile.seek(0);
        }
        byte[] bytes = new byte[(int) (len - pos)];
        randomaccessfile.read(bytes);
        if (charset == null) {
          return new String(bytes);
        } else {
          return new String(bytes, charset);
        }
      }
    } catch (FileNotFoundException e) {
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (randomaccessfile != null) {
        try {
          randomaccessfile.close();
        } catch (Exception e2) {
        }
      }
    }
    return null;
  }

  public static String readLastLine(File file) {
    return readLastLine(file, Charset.defaultCharset().displayName());
  }

  public static void createFolder(String folderPath) {
    File file = new File(folderPath);
    if(!file.exists()) {
      file.mkdirs();
    }
  }

  public static String convertInpustreamToString(InputStream inputStream,String encoding){
    StringBuffer stringBuffer = new StringBuffer();
    try {
      InputStreamReader isr= new InputStreamReader(inputStream, encoding);
      BufferedReader br = new BufferedReader(isr);
      String line;
      while ((line = br.readLine()) != null) {
        stringBuffer.append(line);
      }
    } catch (Exception e) {
    }
    return stringBuffer.toString();
  }

  public static String convertInpustreamToString(InputStream inputStream){

    return convertInpustreamToString(inputStream,Charset.forName("utf-8").displayName());
  }
}
