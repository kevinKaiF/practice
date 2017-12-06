package y2017.m08.d29;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/29
 */
public class TestConcurrentHashMap {

    @Test
    public void testMap() {
        Map map = new ConcurrentHashMap();
        map.put("test", 2);
        map.put("test1", 2);
        map.put("test2", 2);
        System.out.println(map);
    }
}
