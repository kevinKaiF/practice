package y2016.m06.day20160612;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
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
