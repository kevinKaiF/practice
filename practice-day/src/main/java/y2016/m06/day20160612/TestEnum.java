package y2016.m06.day20160612;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-12 PM05:10
 */
public class TestEnum {
    enum Type{
        RED,
        GREEN;
    }

    @Test
    public void testEnum() {
        System.out.println(Type.GREEN.equals(Type.GREEN));
    }
}
