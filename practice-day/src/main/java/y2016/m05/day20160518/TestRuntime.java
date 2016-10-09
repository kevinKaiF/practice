package y2016.m05.day20160518;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-18 PM01:17
 */
public class TestRuntime {
    @Test
    public void testRuntime() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void testSubString() {
        String str = "''";
        System.out.println(str.substring(1, str.length()));
    }
}
