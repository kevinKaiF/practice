package y2016.m06.day20160617;

import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-17 AM11:32
 */
public class TestEncode {
    @Test
    public void testEncode() throws UnsupportedEncodingException {
        String s = "1æ -ä¹¦æ--ä»¶-å--æ--ä»¶+-+å¤-å-¶";
        System.out.println(new String(s.getBytes("ISO-8859-1"), "utf-8"));
    }

    @Test
    public void test() throws UnsupportedEncodingException {
        String s = "1%E6%A0%87%E4%B9%A6%E6%96%87%E4%BB%B6%2B-%2B%E5%8E%9F%E6%96%87%E4%BB%B6+-+%E5%A4%8D%E5%88%B6.png%2F1%E6%A0%87%E4%B9%A6%E6%96%87%E4%BB%B6-%E5%8E%9F%E6%96%87%E4%BB%B6.png";
        System.out.println(URLDecoder.decode(s, "UTF-8"));
    }

    @Test
    public void testEncode1() throws UnsupportedEncodingException {
        String s = "1标书文件-原文件 - 复制.png/1标书文件-原文件.png";
        System.out.println(URLEncoder.encode(s, "UTF-8"));
    }

    @Test
    public void testFileName() {
        // getName是包含后缀名的,如果是目录直接返回目录名
        File file = new File("C:\\Users\\Administrator\\Desktop\\constant\\add.py");
        System.out.println(file.getName());
    }

    @Test
    public void testEncode2() throws UnsupportedEncodingException {
        String s = "1标书文件-原文件 - 复制.png";
        System.out.println(new String(s.getBytes(), StandardCharsets.ISO_8859_1));
        System.out.println(new String(s.getBytes(), "UTF-8"));
    }

    @Test
    public void testHashSet() {
        // 如果已存在返回false
        Set<String> set = new HashSet<>();
        System.out.println(set.add("name"));
        System.out.println(set.add("name"));

    }

}
