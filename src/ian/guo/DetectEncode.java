package ian.guo;

import ian.guo.util.FileUtils;
import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetectEncode {

  private static Set<String> EXT_NAME_SET = new HashSet<>();
  static {
    EXT_NAME_SET.add("css");
    EXT_NAME_SET.add("csv");
    EXT_NAME_SET.add("js");
    EXT_NAME_SET.add("sql");
    EXT_NAME_SET.add("java");
    EXT_NAME_SET.add("xml");
    EXT_NAME_SET.add("html");
    EXT_NAME_SET.add("classpath");
    EXT_NAME_SET.add("txt");
  }

  public static void main(String[] args) throws Exception {

    File rootFolder = new File("D:\\nnn-project\\workspace-local-git\\batch-sm");

    List<File> fs = new ArrayList<>();
    FileUtils.listFile(rootFolder, fs);

    fs = fs.stream().filter(f -> {

      String name = f.getName();
      int index = name.lastIndexOf('.');
      if (index >= 0 && index != name.length() - 1) {
        name = name.substring(index + 1);
        return EXT_NAME_SET.contains(name.toLowerCase());
      }
      return false;
    }).collect(Collectors.toList());

    int except = 0;
    for (File f : fs) {
      String encode = getFileEncode(f.getAbsolutePath());
      if (!"utf-8".equalsIgnoreCase(encode) && !"US-ASCII".equalsIgnoreCase(encode)) {
        String path = f.getAbsolutePath().substring(rootFolder.getAbsolutePath().length() + 1);
        System.out.println(encode + "\t" + path);
        except++;
      }
    }
    System.out.println("文件个数： " + fs.size());
    System.out.println("非UTF-8编码文件个数： " + except);
  }

  private static final CodepageDetectorProxy DETECTOR = CodepageDetectorProxy.getInstance();
  static {
    DETECTOR.add(new ParsingDetector(false));
    DETECTOR.add(ASCIIDetector.getInstance());
    DETECTOR.add(UnicodeDetector.getInstance());
    DETECTOR.add(JChardetFacade.getInstance());
  }

  private static String getFileEncode(String path) throws Exception {
    File f = new File(path);
    Charset charset = DETECTOR.detectCodepage(f.toURI().toURL());
    return charset.name();
  }
}
