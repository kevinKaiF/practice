package y2016.m09.d08;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-08 PM04:44
 */
public class TestString {
    @Test
    public void testStr() {
        String str = "000027";
        String str1 = "000028";
        System.out.println(Integer.valueOf(str) < Integer.valueOf(str1));
        System.out.println(Integer.valueOf(str));
    }
}
