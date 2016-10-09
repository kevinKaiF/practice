package y2016.m08.day20160816;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-16 PM04:36
 */
public class SkipList {
    @Test
    public void testConcurrentHashSet() {
        Set<String> concurrentSet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    }

}
