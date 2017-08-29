package y2017.m04.d28;

import org.junit.Test;
import org.springframework.util.AutoPopulatingList;
import org.springframework.util.comparator.NullSafeComparator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/28
 */
public class TestSpringCoreUtils {
    @Test
    public void testCollectionsSort() {
        NullSafeComparator<String> nullSafeComparator = new NullSafeComparator<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }, false);
        List<String> list = Arrays.asList(null, "test1", "test2", "test3");
        Collections.sort(list, nullSafeComparator);
        System.out.println(list);
    }

    @Test
    public void testAutoPopulatingList() {
        org.springframework.util.AutoPopulatingList<String> autoPopulatingList = new AutoPopulatingList(String.class);
    }

    @Test
    public void testArray() throws ClassNotFoundException {
        System.out.println(String.class);
        System.out.println(String[].class);
        System.out.println(String[][].class);
        long st = System.nanoTime();
        Class<?> aClass = Class.forName("[Ljava.lang.String;");
        long et = System.nanoTime();
        System.out.println(et - st);
        st = System.nanoTime();
        Class<?> aClass1 = Array.newInstance(String.class, 0).getClass();
        et = System.nanoTime();
        System.out.println(et - st);
        System.out.println(aClass);
        System.out.println(aClass1);
    }
}
