package y2016.m07.day20160707;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-07 PM05:42
 */
public class TestString {
    @Test
    public void testString() {
        String s = "12\001";
        System.out.println(Arrays.toString(s.split("\001")));
    }

    @Test
    public void testExit() {
        System.exit(0);
    }
}
