package y2016.m07.day20160722;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-22 PM04:01
 */
public class TestClass {
    @Test
    public void testClass() {
        System.out.println(ToStringBuilder.reflectionToString(ArrayList.class.getTypeParameters()[0]));;
    }

    @Test
    public void testEnclosingClass() {
        A classA = new A() {
            @Override
            public void sayHello() {
                System.out.println("hello");
            }
        };

        // 获取运行该实例的方法（testEnclosingClass）
        Method enclosingMethod = classA.getClass().getEnclosingMethod();
        System.out.println(ToStringBuilder.reflectionToString(enclosingMethod));

        // 获取该类的外部类，只有内部类才会有结果，否则为null
        Class enclosingClass = classA.getClass().getEnclosingClass();
        System.out.println(ToStringBuilder.reflectionToString(enclosingClass));
        System.out.println(enclosingClass.getName());

        Constructor constructor = classA.getClass().getEnclosingConstructor();
        System.out.println(ToStringBuilder.reflectionToString(constructor));

        System.out.println(TestClass.class.getEnclosingClass());

    }


    interface A {
        public void sayHello();
    }
}
