package ian.guo;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.File;
import java.nio.charset.Charset;

public class DetectEncode {

  public static void main(String[] args) throws Exception {

    CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
    detector.add(new ParsingDetector(false));
    detector.add(ASCIIDetector.getInstance());
    detector.add(UnicodeDetector.getInstance());
    detector.add(JChardetFacade.getInstance());
    File f = new File("C:\\Users\\guoyl.SJNS\\Desktop\\結合テスト質問SMW05222.txt");
    Charset charset = detector.detectCodepage(f.toURI().toURL());
    System.out.println(charset.name());
  }
}
