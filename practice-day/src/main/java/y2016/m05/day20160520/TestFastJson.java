package y2016.m05.day20160520;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-20 PM05:14
 */
public class TestFastJson {
    @Test
    public void test1() {
        String jsonStr = "{\"name\" : \"kevin\", \"age\" : 21}";
        Map map = JsonUtils.json2Object(jsonStr, HashMap.class, "");
        System.out.println(map.size());
    }
}
