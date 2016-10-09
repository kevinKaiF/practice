package y2016.m05.day20160531;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-31 AM09:44
 */
public class TestReference {


    @Test
    public void testWeakReference() {
        StringBuffer s = new StringBuffer("123");
        WeakReference<StringBuffer> weakReference = new WeakReference<StringBuffer>(s);
        System.out.println(weakReference.get());
        weakReference.clear();
        System.out.println(weakReference.get());

        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
        weakHashMap.put("name", "kevin");

        System.out.println(weakHashMap.get("name"));
        System.out.println();
    }

    @Test
    public void testWeakHashMap() {
        WeakHashMap wMap = new WeakHashMap();
        Person p1 = new Person("张三");
        Person p2 = new Person("李四");
        wMap.put(p1, "zs");
        wMap.put(p2, "ls");
        p1 = null;

        //gc,不保证运行
        try {
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Object o : wMap.entrySet()) {
            System.out.println(o);
        }
    }

    @Test
    /**
     * 由于String的特殊性，String的直接量是保存在常量池中，即在方法区中，并没有被垃圾回收
     * 但是呢， 可以使用new String的方式就OK
     */
    public void testWeak2() {
        WeakHashMap wMap = new WeakHashMap();
        String s1 = "s1";
        String s2 = "s2";
        wMap.put(s1, "zs");
        wMap.put(s2, "ls");
        s1 = null;

        //gc,不保证运行
        try {
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Object o : wMap.entrySet()) {
            System.out.println(o);
        }
    }

    @Test
    public void testWeak3() {
        WeakHashMap wMap = new WeakHashMap();
        String s1 = new String("s1");
        String s2 = new String("s2");
        wMap.put(s1, "zs");
        wMap.put(s2, "ls");
        s1 = null;
        s2 = null;

        //gc,不保证运行
        try {
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Object o : wMap.entrySet()) {
            System.out.println(o);
        }
    }

    /**
     * 像softReference、weakReference，在enqueue之前，垃圾收集器将其回收。而phantomReference并非如此。
     * 当所有phantomReference类型所引用的对象被clean或者所引用的对象不可达时，才enqueue。
     * 即当PhantomReference所引用的对象完全从物理内存中移除，才会enqueue
     */
    @Test
    public void testPhantomReference() {
        String s = new String("test");
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantomReference = new PhantomReference<>(s, queue);

        System.out.println(queue.poll());
        System.out.println(queue.poll() == phantomReference);

//        s = null;

        try {
            System.gc();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("enqueue ? " + phantomReference.enqueue());
        System.out.println("isEnqueued ?" + phantomReference.isEnqueued());
        System.out.println(queue.poll() == phantomReference);

        System.out.println(s);
    }


    public static void main(String[] args) {
        Person person = new Person("");
    }

    private static class Person {
        static {
            System.out.println("static");
        }
        private String name;

        public Person() {
            super();
            System.out.println("default constructor");
        }

        // 通常情况下，本类的构造方法是独立的
        public Person(String name) {
            this.name = name;
            System.out.println("constructor");
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
