package y2017.m04.d25;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/25
 */
public class TestJson {
    @Test
    public void testJson() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("./profile.txt");
        byte[] bytes = IOUtils.toByteArray(inputStream);
        Map parse = JSONObject.parseObject(bytes, Map.class);
        System.out.println(parse);
    }

    @Test
    public void testProperties() {
        System.out.println(getProperties());
    }

    public Map<Object, Object> getProperties() {
        Properties properties = new Properties();
        properties.put("name", "lev");
        return Collections.unmodifiableMap(properties);
    }

}
