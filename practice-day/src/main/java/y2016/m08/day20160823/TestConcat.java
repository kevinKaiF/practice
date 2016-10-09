package y2016.m08.day20160823;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-23 PM03:37
 */
public class TestConcat {
    /**
     * 字符串拼接的时候，可以拼接任意类型，但实际上编译成字节码后是按照StringBuilder拼接的.
     *
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-23 15:54:33
     */
    @Test
    public void testConcat() {
        System.out.println("test : " + new Object());
        System.out.println("test : " + new Object().toString());
    }
}
