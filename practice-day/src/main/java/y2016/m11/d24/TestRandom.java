package y2016.m11.d24;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/24
 */
public class TestRandom {
    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int nextInt = random.nextInt();
            System.out.println(Integer.toBinaryString(nextInt));
            System.out.println(Integer.toBinaryString(nextInt | 0x0100));
            System.out.println("===============================");
        }
    }

    @Test
    public void testOr() {
        System.out.println(2 | 0x10);
    }

    @Test
    public void testLinkedHashMap() {
        MyLinkedHashMap<String, String> map = new MyLinkedHashMap<>();
        // 初始化完毕后,header的before,after均指向header自身,即header.before == header.after
        System.out.println(map.header == map.header.before);
        System.out.println(map.header == map.header.after);
        System.out.println(ToStringBuilder.reflectionToString(map.header));
        map.put("test1", "test1");
        System.out.println(ToStringBuilder.reflectionToString(map.header));
        // 第一次put后呢,header的before和after均指向第一个Entry,即header.before == Entry[0],header.after == Entry[0]
        System.out.println(map.header.after == map.header.before);
        map.put("test2", "test1");
        System.out.println(ToStringBuilder.reflectionToString(map.header));
        System.out.println(map.header.after == map.header.before.before);
        System.out.println(map.header == map.header.before.before.before);
        map.put("test3", "test1");
        System.out.println(ToStringBuilder.reflectionToString(map.header));
        System.out.println(ToStringBuilder.reflectionToString(map.header));
        System.out.println(map.toString());
    }

    @Test
    public void testTreeMap() {
        Map<String, String> map = new MyTreeMap<>();
        map.put("test1", "test1");
        map.put("test2", "test2");
        map.put("test3", "test3");
        System.out.println(map);
    }

    @Test
    public void testTreeMap1() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("name", "kevin");
        map.put("age", "22");
        System.out.println(map);
    }
}
