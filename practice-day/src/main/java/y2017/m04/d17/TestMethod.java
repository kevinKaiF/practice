package y2017.m04.d17;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/17
 */
public class TestMethod {
    /**
     * 从概念上：
     * isSynthetic是人工构造出的方法，在类定义中是不存在的，比如内部类，泛型的桥接实现
     * isBridge是对泛型接口的具体实现
     * isVarArgs是方法参数为动态参数列表
     * 从字节码层面上：
     * isSynthetic方法有synthetic关键字
     * isBridge方法有bridge关键字
     * isVarArgs方法有varargs关键字
     */
    @Test
    public void testIsSynthetic() {
        Class<?> clazz = DemoMethod.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (declaredMethods != null) {
            for (Method declaredMethod : declaredMethods) {
                System.out.println(declaredMethod + ", " + declaredMethod.getName() + " isSynthetic : " + declaredMethod.isSynthetic()
                        + ", isBridge : " + declaredMethod.isBridge() + ", isVarArgs : " + declaredMethod.isVarArgs());
            }
        }

    }


    /**
     * 数组的equals调用的Object的equals方法，不能准确判断两个数组是否equals，应该使用{@link java.util.Arrays#equals}的方法
     * 同样hashCode也是调用Object的hashCode方法，需要Arrays.deepHashCode();
     */
    @Test
    public void testArray() {
        String[] arr1 = {"test1"};
        String[] arr2 = {"test1"};
        boolean equals = arr1.equals(arr2);
        System.out.println(equals);
        System.out.println(Arrays.equals(arr1, arr2));

        System.out.println(arr1.hashCode());
        System.out.println(arr2.hashCode());
        System.out.println(Arrays.deepHashCode(arr1));
        System.out.println(Arrays.deepHashCode(arr2));
    }

}
