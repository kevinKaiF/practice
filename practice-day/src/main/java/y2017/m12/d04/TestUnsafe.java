package y2017.m12.d04;

import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.lang.reflect.Field;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/12/4
 */
@SuppressWarnings("deprecation")
public class TestUnsafe {
    Unsafe unsafe;

    @Before
    /**
     *  Unsafe无法反射获取getUnsafe方法
     */
    public void init() {
        Field theUnsafe = null;
        try {
            theUnsafe =Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            this.unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        try {
//            Method getUnsafe = Unsafe.class.getMethod("getUnsafe");
//            this.unsafe = (Unsafe) getUnsafe.invoke(null);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testUnsafe() throws NoSuchMethodException {
        System.out.println(unsafe);
//        System.out.println(String.class.getDeclaredMethod("valueOf", boolean.class));
//        System.out.println(Demo.class.getDeclaredMethod("test"));
        int arrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
        System.out.println(arrayBaseOffset);

        byte[] bytes = new byte[16];
        unsafe.putBoolean(bytes, arrayBaseOffset + 1, true);
        System.out.println(unsafe.getBoolean(bytes, arrayBaseOffset + 0));
        System.out.println(unsafe.getBoolean(bytes, arrayBaseOffset + 1));
        System.out.println(unsafe.getBoolean(bytes, arrayBaseOffset + 2));
    }

    static class Demo {
        @CallerSensitive
        public static void test() {}
    }
}
