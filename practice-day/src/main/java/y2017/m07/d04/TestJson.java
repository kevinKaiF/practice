package y2017.m07.d04;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/4
 */
public class TestJson {
    @Test
    public void testJson() {
        String jsonString = "{\n" +
                "                                        \"username\":\"\", \n" +
                "                                        \"password\":\"\",\n" +
                "                                        \"host\":\"\",\n" +
                "                                        \"port\":\"\",\n" +
                "                                        \"jdbcUrl\":\"\",\n" +
                "                                        \"slaveId\":\"\",\n" +
                "                                        \"whiteFilter\":\"white\\\\..*\",\n" +
                "                                        \"blackFilter\":\"black\\\\..*\"\n" +
                "}";
        Map t = JSON.parseObject(jsonString, Properties.class);
//        JSONObject jsonObject = (JSONObject) t;
        System.out.println(t);
        System.out.println("\\.");
    }
}
