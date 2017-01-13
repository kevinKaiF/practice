package y2016.m12.d29;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class TestIntMap {
    @Test
    public void testIntMap() {
        IntMap intMap = new IntMap();
        intMap.put("test", 10);
        intMap.put("test1", 11);
        intMap.put("test2", 12);
        intMap.put("test3", 13);
        intMap.put("test4", 14);
        intMap.put("test5", 15);
        intMap.put("test6", 16);
        intMap.put("test7", 17);
        intMap.put("test8", 18);
        intMap.put("test9", 19);
        intMap.put("test10", 20);
        intMap.put("test11", 21);
        intMap.put("test12", 22);
        intMap.put("test13", 23);
        intMap.put("test14", 24);
        intMap.put("test15", 25);
        intMap.put("test16", 26);
        intMap.put("test17", 27);

        System.out.println(intMap.size());
        System.out.println(intMap.get("test22"));

        int test2 = intMap.put("test2", 222);
        System.out.println(test2);
        System.out.println(intMap.get("test2"));

        System.out.println(intMap);
    }
}
