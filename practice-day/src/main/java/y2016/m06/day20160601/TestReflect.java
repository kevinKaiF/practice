package y2016.m06.day20160601;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-01 AM09:34
 */
public class TestReflect {
    @Test
    public void testReflect() {
       // ApplicationContextAwareProcessor applicationContextAwareProcessor
//        FileDescriptor
//        FileOutputStream
    }
    @Test
    public void testSocket() {
//        Socket

//        InputStreamReader
        System.out.println(Charset.defaultCharset().name());
    }

    @Test
    public void testChinese() throws UnsupportedEncodingException {
        System.out.println("中".getBytes().length);
        System.out.println("中".toCharArray()[0]);
    }

    @Test
    public void testByte() {
        byte b = (byte) 255;
        System.out.println(b);
        System.out.println(b & 0xff);

    }
}
