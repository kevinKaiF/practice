package y2016.m07.day20160723;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-23 AM10:10
 */
public class TestLocalClass {
    private String properties;
    @Test
    public void testLocalClass() {
        class LocalClass {
            private String name;
            private int age;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                properties = null;
                this.age = age;
            }

            // inner class禁止声明static的属性或者方法
            public /*static*/ void show() {

            }

            private /*static*/ String c;

        }

        LocalClass localClass = new LocalClass();
        localClass.setName("test");
        localClass.setAge(22);

        System.out.println(LocalClass.class.isLocalClass());
        // 如果存在，说明该类是创建于某个方法内部
        System.out.println(ToStringBuilder.reflectionToString(LocalClass.class.getEnclosingMethod()));
        // 如果存在，说明该类是创建于某个类内部
        System.out.println(ToStringBuilder.reflectionToString(LocalClass.class.getEnclosingClass()));
        // 如果存在，说明该类是创建于某个构造方法内部
        System.out.println(ToStringBuilder.reflectionToString(LocalClass.class.getEnclosingConstructor()));

        System.out.println(ToStringBuilder.reflectionToString(new Object().getClass().getEnclosingMethod()));

        System.out.println(Object.class.getSigners());

    }

    @Test
    public void testLocalClass2() {
        // 是否是系统生成的，比如典型的动态代理会生成系统类
        System.out.println(A.class.isSynthetic());
        System.out.println(System.class.isSynthetic());
//        URLClassLoader
    }

    class A {
        private /*static*/ String c;
        private /*static*/ void show() {};
    }
}
