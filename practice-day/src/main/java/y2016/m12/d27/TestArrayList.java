package y2016.m12.d27;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/27
 */
public class TestArrayList {
    @Test
    public void test1() {
        List<String> stringList = new ArrayList<>();
        stringList.add("test");
        stringList.add("test");
        stringList.add("test");

        Map<String, Object> map = new HashMap<>();
        map.put("list", new ArrayList<String>(stringList));

        stringList.clear();
        System.out.println(map);
    }

    @Test
    public void test2() {
        System.out.println(~0);
    }
}
