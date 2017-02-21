package com.makenv.model.mc.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by alei on 2016/9/1.
 */
public class ZipUtil {
  static final int BUFFER = 2048;

  public static void unzip(String fileName, String targetDir) throws IOException {
    ZipFile zipFile = new ZipFile(fileName);
    Enumeration emu = zipFile.entries();
    int i = 0;
    while (emu.hasMoreElements()) {
      ZipEntry entry = (ZipEntry) emu.nextElement();
      //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
      File file = new File(targetDir + entry.getName());
      if (entry.isDirectory()) {
        file.mkdirs();
        continue;
      }
      BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
      //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
      //而这个文件所在的目录还没有出现过，所以要建出目录来。
      File parent = file.getParentFile();
      if (parent != null && (!parent.exists())) {
        parent.mkdirs();
      }
      FileOutputStream fos = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

      int count;
      byte data[] = new byte[BUFFER];
      while ((count = bis.read(data, 0, BUFFER)) != -1) {
        bos.write(data, 0, count);
      }
      bos.flush();
      bos.close();
      bis.close();
    }
    zipFile.close();
  }

  public static void zipIt(String sourceFolder, String id, String zipFile) throws IOException {
    byte[] buffer = new byte[BUFFER];
//    String source = "";
    FileOutputStream fos = null;
    ZipOutputStream zos = null;
    List<String> fileList = generateFileList(new File(sourceFolder), sourceFolder.length() + 1);
    try {
      fos = new FileOutputStream(zipFile);
      zos = new ZipOutputStream(fos);

      System.out.println("Output to Zip : " + zipFile);
      FileInputStream in = null;

      for (String file : fileList) {
        System.out.println("File Added : " + file);
//        ZipEntry ze = new ZipEntry(sourceFolder + File.separator + file);
        ZipEntry ze = new ZipEntry(id + File.separator + file);
        zos.putNextEntry(ze);
        try {
          in = new FileInputStream(sourceFolder + File.separator + file);
          int len;
          while ((len = in.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
          }
        } finally {
          in.close();
        }
      }
      zos.closeEntry();
    } finally {
      zos.close();
    }
  }

  private static List<String> generateFileList(File node, int length) {
    List<String> fileList = new ArrayList<>();
    // add file only
    if (node.isFile()) {
      fileList.add(generateZipEntry(node.toString(), length));
    } else if (node.isDirectory()) {
      String[] subNote = node.list();
      for (String filename : subNote) {
        fileList.addAll(generateFileList(new File(node, filename), length));
      }
    }
    return fileList;
  }

  private static String generateZipEntry(String file, int length) {
    return file.substring(length, file.length());
  }
}
