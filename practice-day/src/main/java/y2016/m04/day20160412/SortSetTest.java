package y2016.m04.day20160412;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-12 AM11:18
 */
public class SortSetTest {
    @Test
    public void test1() {
        // SortedSet唯一的实现者只有TreeSet和ConcurrentSkipListSet不考虑内部类
        SortedSet sortedSet = new TreeSet();
        SortedSet sortedSet2 = new ConcurrentSkipListSet();

        // Collections的工厂方法中的unmodifiableSortedSet参数是SortedSet
        SortedSet sortedSet1 = Collections.unmodifiableSortedSet(new TreeSet(new HashSet<String>()));
    }
}
