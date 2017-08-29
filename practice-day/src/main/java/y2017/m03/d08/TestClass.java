package y2017.m03.d08;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/8
 */
public class TestClass {
    @Test
    public void testClass() {
        System.out.println(Object.class.isAssignableFrom(String.class));
    }

    @Test
    public void testCharArr() {
        final char[] HEXDUMP_TABLE = new char[256 * 4];
        final char[] DIGITS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; i++) {
            HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 0x0F];
            HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 0x0F];
        }

        System.out.println(Arrays.toString(HEXDUMP_TABLE));
    }

    @Test
    public void testTryFinally() {
        System.out.println(getName());
    }

    private CharSequence getName() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("22");
            /**
             * 如果是按toString的方式，会产生新的引用，所以与finally的引用不一致
             */
//            return sb.toString(); // 22
            return sb; // 2233
        } finally {
            sb.append("33");
            System.out.println(sb.toString());
        }
    }

    @Test
    public void testTryFinally1() {
        System.out.println(getList());
    }

    private List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("add");
        list.add("add");

        try {
            return list;
        } finally {
//            list.clear();
            list.add("33cc");
        }
    }

    @Test
    public void testClass2() {
        A<Object> objectA = new A<>();
//        Object o = objectA.get(objectA); // don't compile
    }

    class A<T> {
        public T get(A<? extends String> a) {
            return (T) this;
        }
    }

    @Test
    public void testStackTrace() {
        String s = null;
        try {
            s.toLowerCase();
        } catch (Exception e) {
            /**
             * 设置{@link Throwable#stackTrace}为空数组，栈数据为空，所以打印不出栈信息
             */
            e.setStackTrace(new StackTraceElement[0]);
            /**
             * Throwable重写了toString,会判断message ? (message + this.toString) : this.toString
             */
            e.printStackTrace();
        }
    }

    @Test
    public void testOverride() {
        E e = new E();
        e.run(null);
    }

    interface C<T> {
        public abstract void run(T t);
    }

    interface D extends C<T> {
        @Override
        void run(T t);
    }

    class E implements D {
        private T t;
        @Override
        public void run(T t) {

        }
    }

    private Unsafe unsafe;


}
