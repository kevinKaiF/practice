package y2016.m08.day20160824;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-24 PM03:02
 */
public class TestAtomic {
    /**
     * 尽管是AtomicReference，也需要注意引用共享的问题.
     *
     * @author : kevin
     * @date : 2016-08-24 15:19:25
     */
    @Test
    public void testAtomicReference() {
        AtomicReference<List<String>> atomicReference = new AtomicReference<>();
        List<String> origin = new ArrayList<String>() {
            {
                add("test");
                add("test1");
                add("test2");
            }
        };

        atomicReference.set(origin);
        origin.add("test3");

        List<String> list = atomicReference.get();
        System.out.println(list);

        atomicReference.compareAndSet(list, list.subList(1, 3));
        List<String> list1 = atomicReference.get();
        System.out.println(list1);
        list1.add("test4");
        System.out.println(atomicReference.get());
    }

    public String format1() {
        return format("");
    }
}
