package y2017.m07.d26;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/26
 */
public class TestCharset {
    @Test
    public void testCharset() {
        System.out.println(StandardCharsets.UTF_8.name());
        System.out.println(StandardCharsets.UTF_8.toString());
    }

    @Test
    public void testString() {
        String s = "this is a test";
        String[] split = s.split("\f");
        System.out.println((byte)'\r' & 0xFF);  // 13
        System.out.println((byte)'\n' & 0xFF);  // 10
        System.out.println((byte)'\t' & 0xFF);  // 9
        System.out.println((byte)'\f' & 0xFF);  // 12
        System.out.println((byte)'\b' & 0xFF);  // 8
        System.out.println((byte)'\\' & 0xFF);  // 92
        System.out.println((byte)'\'' & 0xFF);  // 39
        System.out.println((byte)'\"' & 0xFF);  // 34
        System.out.println(split.length);
    }
}
