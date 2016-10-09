package y2016.m08.day20160825;

import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-25 AM10:27
 */
public class TestJ {
    @Test
    public void test1() {
        // null可以强转
//        System.out.println(((Object) null).toString());

        String s = "hello";
        String s1 = "hel" + new String("lo");
        String s2 = "hel" + "lo"; // 编译直接优化
        System.out.println(s == s1);
        System.out.println(s == s2);
    }

    private static class HelloA{
        public HelloA(){
            System.out.println("HelloA");
        }

        { System.out.println("I'm A class"); }

        static {
            System.out.println("static A");
        }
    }
    private static class HelloB extends HelloA{
        public HelloB(){
            System.out.println("HelloB");
        }

        { System.out.println("I'm B class"); }

        static {
            System.out.println("static B");
        }


        /**
         * main方法在B类内部，所以main方法执行的时候，B类已经初始化了.
         *
         * 先父类静态初始化，
         * 父类动态初始化，
         * 父类的构造函数初始化，
         * 子类静态初始化，
         * 子类动态初始化，
         * 子类的构造函数初始化，
         *
         * @param args the args
         * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
         * @date : 2016-08-25 10:35:43
         */
        public static void main(String[] args) {
            System.out.println("main start");
            new HelloB();
            new HelloB();
            System.out.println("main end");
        }

    }




}
