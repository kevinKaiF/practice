package y2016.m07.day20160723;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-23 AM11:56
 */
public class TestEnum {
    @Test
    public void testEnum() {
        Type.BLUE.ordinal();
        Enum s = Type.RED;
        System.out.println(s);
    }

    enum Type {
        RED,
        BLUE;
    }

    interface TestInterface {

    }

    enum COLOR implements TestInterface {

    }

//    class TestClass extends Enum<Type> {
//
//    }
}
