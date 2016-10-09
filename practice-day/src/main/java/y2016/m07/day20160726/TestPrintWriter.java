package y2016.m07.day20160726;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-26 AM09:07
 */
public class TestPrintWriter {
    @Test
    public void testPrintWriter() {
        try {
            PrintWriter writer = new PrintWriter("F:\\test.txt", "GBK");
            writer.write("你好");
            writer.println("你好");
            writer.println("你好");
            writer.println("你好");
            writer.println("你好");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
