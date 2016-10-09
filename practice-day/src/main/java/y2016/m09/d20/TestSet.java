package y2016.m09.d20;

import org.junit.Test;
import y2016.m08.day20160816.SkipList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-20 PM02:28
 */
public class TestSet {
    @Test
    public void testSet() {
        Set<String> set = new ConcurrentSkipListSet<String>();
        set.add("name");
        System.out.println(set);
        set.addAll(new ArrayList<String>() {
            {
                add("name");
            }
        });
        System.out.println(set);
        Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        Collections.newSetFromMap(new ConcurrentSkipListMap<Object, Boolean>());
    }
}
