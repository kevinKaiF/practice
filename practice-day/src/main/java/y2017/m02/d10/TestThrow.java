package y2017.m02.d10;

import cn.bidlink.framework.core.utils.json.jackson.JsonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-02-10 PM05:24
 */
public class TestThrow {
    @Test
    public void testThrow() {
        try {
            String s = null;
            s.toString();
        } finally {
            System.out.println("fin");
        }
    }

    @Test
    public void testJson() {
        String json = "{\"reviewIndices\":[{\"totalNum\":0,\"secondIndexItems\":[{\"itemNum\":0,\"secondIndexName\":\"a\",\"data\":{\"1100074695\":1,\"1113172075\":1,\"1113172076\":0,\"1113172077\":1,\"1113172078\":0,\"1113172079\":1,\"1113172080\":0,\"1113172081\":1}}],\"firstIndexName\":\"a\"},{\"totalNum\":0,\"secondIndexItems\":[{\"itemNum\":0,\"secondIndexName\":\"a\",\"data\":{\"1100074695\":1,\"1113172075\":1,\"1113172076\":0,\"1113172077\":1,\"1113172078\":0,\"1113172079\":1,\"1113172080\":0,\"1113172081\":1}}],\"firstIndexName\":\"a\"}],\"result\":[{\"id\":\"1113172076\",\"name\":\"gygy02\",\"value\":0},{\"id\":\"1113172078\",\"name\":\"gygy04\",\"value\":0},{\"id\":\"1113172080\",\"name\":\"gygy06\",\"value\":0},{\"id\":\"1113172079\",\"name\":\"gygy05\",\"value\":1},{\"id\":\"1113172075\",\"name\":\"gygy01\",\"value\":1},{\"id\":\"1113172081\",\"name\":\"gygy07\",\"value\":1},{\"id\":\"1100074695\",\"name\":\"gygy99\",\"value\":1},{\"id\":\"1113172077\",\"name\":\"gygy03\",\"value\":1}]}";
        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
        Map map = JsonUtils.json2Object(json, Map.class, null);
        System.out.println(map);
    }
}
