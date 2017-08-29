package y2017.m04.d30;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/30
 */
public class TestMethod {
    @Test
    public void testMethod() {
        try {
            // 静态方法反射调用的时候invoke的第一个参数可以为空
            Object getName = Logger.class.getDeclaredMethod("getStaticName").
                    invoke(null, null);
            System.out.println(getName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static class Logger {
        public String getName() {
            return "2";
        }

        public static String getStaticName() {
            return "33";
        }
    }

    /**
     * } else if (c > '\u0000' && c <= '\u001f'
     || c >= '\u007f' && c <= '\u009F'
     || c >= '\ud800' && c <= '\uf8ff'
     || c >= '\ufff0' && c <= '\uffff') {
     *
     */
    @Test
    public void testChar() {
        System.out.println((int)'\u001f');
        for (char i = '\u0000'; i < '\u001f'; i++) {
            System.out.println(i);
        }

        for (char i = '\u007f'; i < '\u009F'; i++) {
            System.out.println(i);
        }

        for (char i = '\ud800'; i < '\uf8ff'; i++) {
            System.out.println(i);
        }

    }

    @Test
    public void testChar2() {
        System.out.println((int)'A');
        System.out.println((int)'Z');
        System.out.println((int)'a');
        System.out.println((int)'z');
        System.out.println(Integer.toHexString('z'));
        System.out.println((char)123);  // {
        System.out.println((char)124);  // |
        System.out.println((char)125);  // }
        System.out.println((char)126);  // ~
        System.out.println((char)127);  // u007f
    }

    @Test
    public void testSystemNanoTime() {
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime() / 1000);
        System.out.println(System.currentTimeMillis());
    }
}
