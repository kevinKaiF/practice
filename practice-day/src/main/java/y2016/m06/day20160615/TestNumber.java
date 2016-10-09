package y2016.m06.day20160615;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-15 AM09:37
 */
public class TestNumber {
    @Test
    public void testNumber() {
        int num = 2;
        boolean result = (Object)num instanceof Integer;
//        System.out.println();
    }

    @Test
    public void testNull() {
        Integer num = null;
        boolean result = num instanceof Integer;
//        System.out.println(num instanceof Integer);
    }

    /**
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testLatin() throws UnsupportedEncodingException {
        String s = "ÁúÊ¢¼¯ÍÅ";
        Charset charset = Charset.forName("ISO-8859-1");
        CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(s.getBytes(charset)));
        System.out.println(Arrays.toString(charBuffer.array()));
        System.out.println(new String(s.getBytes(charset), "UTF-8"));
        System.out.println(new String("中国".getBytes("utf-8"), charset));
    }

}
