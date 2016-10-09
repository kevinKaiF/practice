package y2016.m06.day20160606;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-06 AM09:20
 */
public class TestString {
    @Test
    public void testString() {
        System.out.println("1\\.2.3".replace("\\.", "|"));
        System.out.println("1.2.3".replace(".", "|"));
    }

    @Test
    public void testMatch() {
        String replaceDotReg = "\\s*,\\s*|\\s*，\\s*";
        System.out.println("1,3,4，5".contains(","));
        System.out.println("1 , 3, 4，5".replaceAll(replaceDotReg, ","));
    }
}
