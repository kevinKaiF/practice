package y2017.m02.d28;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2017/2/28
 */
public class TestClass {
    @Test
    public void testClass() {
        Byte bytes = Byte.MAX_VALUE;
        System.out.println(bytes.getClass() == Byte.TYPE);
        System.out.println(bytes.getClass() == Byte.class);
        System.out.println(Byte.TYPE);
        System.out.println(Byte.class);
        // 原始类型的class属性对应包装类的TYPE
        System.out.println(byte.class == Byte.TYPE);
        Class<Byte> byteClass = byte.class;
        System.out.println(byteClass);
    }
}
