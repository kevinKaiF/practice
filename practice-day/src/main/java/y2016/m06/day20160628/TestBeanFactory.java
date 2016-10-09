package y2016.m06.day20160628;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-28 AM10:31
 */
public class TestBeanFactory {
    @Test
    public void testBeanFactory() {
//        BeanFactory
    }


    /**
     * instanceOf 和 isInstance是等价的，但是还是有差异
     * 1.isInstance是包括原始数据类型都可以调用的，而且参数是可以是原始类型，也可以是引用类型,凡是原始里类型的调用都返回false
     * 而instanceOf左边的参数不能是原始数据类型，必须是引用类型，且右边必须是不能是原始类型，否则编译不通过
     * 2.instanceOf必须在完整的表达式使用，比如赋值表达式，条件表达式，不能单独存在
     * 而isInstance可以单独存在
     * 3.isInstance是个静态的方法，而instanceOf是字节码指令
     *
     *
     * 而isAssignableFrom只能判断类层次的关系，但是不能判断实例
     *
     */
    @Test
    public void testInstanceOf() {
        B b = new B();
        System.out.println(b instanceof A);
        System.out.println(A.class.isInstance(b));
        System.out.println(A.class.isAssignableFrom(b.getClass()));

        int a = 2;
        System.out.println(int.class.isInstance(2));
        System.out.println((Integer)a instanceof Integer);
    }

    @Test
    public void getConcurrentHashSet() {
        Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(16));
    }

    public static boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader) {
        try {
            ClassLoader target = clazz.getClassLoader();
            if (target == null) {
                return true;
            }
            ClassLoader cur = classLoader;
            if (cur == target) {
                return true;
            }
            while (cur != null) {
                cur = cur.getParent();
                if (cur == target) {
                    return true;
                }
            }
            return false;
        }
        catch (SecurityException ex) {
            // Probably from the system ClassLoader - let's consider it safe.
            return true;
        }
//        ApplicationContextAware
    }


    class A {

    }

    class  B extends A {

    }
}
