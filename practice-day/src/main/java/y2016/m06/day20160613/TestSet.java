package y2016.m06.day20160613;

import y2016.m05.day20160520.JsonUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-13 AM09:30
 */
public class TestSet {
    @Test
    public void testSet() {
        Set<String> set = new LinkedHashSet<>();

        set.add("1");
        set.add("3");
        set.add("2");

        System.out.println(set.toString());
    }

    @Test
    public void testForEach() {
        Set<String> set = new LinkedHashSet<>();

        set.add("1");
        set.add("3");
        set.add("2");

        Iterator<String> iterator = set.iterator(); // 迭代器 每次都是new
        Iterator<String> iterator1 = set.iterator();
        System.out.println(iterator == iterator1); // false
        while (iterator.hasNext()) { // next会将游标下移，hasNext必须和next配合使用
            System.out.println("222");
//            System.out.println("====" + iterator.next());  // 迭代器最好不要嵌套
//            while (iterator1.hasNext()) {
//                System.out.println(iterator1.next());
//                iterator1.remove();
//                break;
//            }
        }
    }

    @Test
    public void testJson() {
//        Map map = new HashMap();
//        map.put("name", "kevin");
//        String s = "{\"name\":\"kevin\"}";
//        System.out.println(JsonUtils.toJson(map, null, new String[]{"name"}, null));;
//        System.out.println(JsonUtils.json2Object(s, Map.class, "").toString());
//        System.out.println(JsonUtils.json2Object("{\"normal\":{\"label\":{\"show\":true}},\"emphasis\":{\"label\":{\"show\":true}}}", Map.class, "").toString());
        System.out.println(JsonUtils.json2Object("{\"formatter\" : true}", Map.class, null));
    }
}
