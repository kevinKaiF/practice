package y2016.m05.day20160520;

import org.junit.Test;

import java.io.FileReader;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-20 AM09:58
 */
public class TestTry {
    @Test
    public void testTry() {
        try {
            FileReader reader = new FileReader("");
            throw new Exception("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 静态绑定和动态绑定
     */
    @Test
    public void testThis() {
        Super sub = new Sub();
        sub.getName();
        System.out.println(sub.name);
        Sub sub1 = (Sub) sub;
        sub1.getName();

        Super super1 = new Super();
        System.out.println(super1.name);

        Sub sub2 = new Sub();
        sub2.getName();
        Super super2 = sub2;
        super2.getName();
    }

    static class Super {
        public Super() {
            System.out.println("constructor default Super");
        }

        public static String getName() {
            System.out.println("Super static method");
            return null;
        }

        protected String name = this.getClass().getName();
    }

    static class Sub extends Super {
        public static String getName() {
            System.out.println("Sub static method");
            return null;
        }
    }
}

