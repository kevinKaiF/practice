package y2016.m08.day20160803;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-03 PM03:01
 */
public class TestEnclosingClass {
    @Test
    public void testEnclosingClass() {
        System.out.println(A.class.getEnclosingClass());
    }

    class A {

    }
}
