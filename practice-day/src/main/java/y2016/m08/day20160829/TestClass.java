package y2016.m08.day20160829;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-29 AM10:16
 */

public class TestClass {
    @Test
    public void testUrl() {
        try {
            URL url = new URL("http://www.baidu.com/test");
            System.out.println(url.getHost() + ":" + url.getPort());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class A {
        private String name;

        public A(String name) {
            this.name = name;
        }

        // 如果有子类必须给子类留下默认构造函数
        public A() {
            System.out.println("default A");
        }
    }

    class B extends A {
        public B() {
            // 调用父类的super构造函数，或者自身的this构造函数，都必须是第一句！！！
            // 所以子类只能调用一个唯一指定的构造函数
            super();
//            this("");
        }

        public B(String name) {
            // 如果不指定调用父类的构造函数，子类会自动调用父类默认的构造函数
            super(name);
        }
    }

    @Test
    public void testClass() {
        B b = new B("v");

    }
}
