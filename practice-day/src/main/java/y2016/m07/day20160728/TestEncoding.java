package y2016.m07.day20160728;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-28 AM11:53
 */
public class TestEncoding {
    @Test
    public void testEncoding() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new FileReader("C:\\Users\\Administrator\\Desktop\\text.txt"));
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
                System.out.println(new String(str.getBytes("ISO-8859-1")));
                System.out.println(new String(str.getBytes("UTF-8")));
                System.out.println(new String(str.getBytes(Charset.forName("GBK"))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testEncoding2() {
        try {
//            String encodedStr = new String("网站统计分析系统-部署配置".getBytes("ISO-8859-1"), "UTF-8");
            String encodedStr = new String("网站统计分析系统-部署配置".getBytes("UTF-8"), "UTF-8");
            System.out.println(encodedStr);
            System.out.println(new String(encodedStr.getBytes("UTF-8"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncoding3() {
        System.out.println(Charset.forName("UTF-9"));
    }
}
