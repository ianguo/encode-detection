package ian.guo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  public static List<String> readFile(String path, String encode) throws IOException {
    
    FileInputStream fis = new FileInputStream(path);
    InputStreamReader streamReader = new InputStreamReader(fis, encode);
    BufferedReader reader = new BufferedReader(streamReader);
    
    List<String> result = new ArrayList<String>();
    String temp = null;
    while ((temp = reader.readLine()) != null) {
      result.add(temp);
    }
    
    reader.close();
    return result;
  }
  
  public static boolean clearFolder(File folder) {
    
    if (!folder.isDirectory()) {
      return true;
    }
    
    for (File f : folder.listFiles()) {
      if (!deleteFile(f)) {
        return false;
      }
    }
    
    return true;
  }
  
  public static boolean deleteFile(File file) {

    if (!file.exists()) {
      return true;
    }
    
    if (file.isFile()) {
      return file.delete();
    } else if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        if (!deleteFile(f)) {
          return false;
        }
      }
      return file.delete();
    }
    return true;
  }
  
  public static void listFolder(File folder, List<File> files) {
    
    if (folder.isDirectory()) {
      for (File f : folder.listFiles()) {
        listFolder(f, files);
      }
      files.add(folder);
    }
  }
  
  public static void listFile(File folder, List<File> files) {
    
    if (folder.isFile()) {
      files.add(folder);
    } else if (folder.isDirectory()) {
      for (File f : folder.listFiles()) {
        listFile(f, files);
      }
    }
  }
  
  public static void copyFile(File srcFile, File destFile) throws IOException {
    
    FileInputStream fis = new FileInputStream(srcFile);
    destFile.getParentFile().mkdirs();
    FileOutputStream fos = new FileOutputStream(destFile);
    byte[] buffer = new byte[1024];
    int len = -1;
    while ((len = fis.read(buffer)) > 0) {
      fos.write(buffer, 0, len);
    }
    fos.flush();
    fos.close();
    fis.close();
  }
  
  public static boolean isFileSame(File src, File dest) throws IOException {
    
    FileInputStream srcFis = new FileInputStream(src);
    FileInputStream destFis = new FileInputStream(dest);
    
    byte[] srcBuf = new byte[1024];
    byte[] destBuf = new byte[1024];
    
    int srcLen = -1;
    int destLen = -1;
    
    boolean isSame = true;
    
    while (true) {
      
      srcLen = srcFis.read(srcBuf);
      destLen = destFis.read(destBuf);
      
      if (srcLen == -1 || destLen == -1) {
        break;
      }

      if (srcLen != destLen) {
        isSame = false;
        break;
      }
      
      for (int i = 0; i < srcLen; i++) {
        if (srcBuf[i] != destBuf[i]) {
          isSame = false;
          break;
        }
      }
    }
    
    srcFis.close();
    destFis.close();
    return isSame;
  }
}
