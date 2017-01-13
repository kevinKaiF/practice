package y2016.m11.d30;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/30
 */
public class TestReference {
    @Test
    public void testReference() {
        AtomicReference<CLHLock> atomicReference = new AtomicReference<>();
        CLHLock lock = new CLHLock();
        CLHLock clhLock = atomicReference.getAndSet(lock);
        CLHLock clhLock1 = atomicReference.getAndSet(lock);
        CLHLock clhLock2 = atomicReference.getAndSet(lock);


        System.out.println(clhLock2 == clhLock1);

    }

    @Test
    public void testCollections() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        // 只是random一个随机位置交换而已！！！
        Collections.shuffle(list);
        System.out.println(list);

        test1(0);

        System.out.println(TestReference.class.getClassLoader().getResource(""));
        System.out.println(TestReference.class.getClassLoader().getResource("."));
    }

    public void test1(int a) {
        if (a == 0) {
            a++;
            test1(a);
            System.out.println("if");
        } else {
            System.out.println("else");
        }
    }

}
