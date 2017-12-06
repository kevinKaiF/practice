package y2017.m08.d09;

import com.alibaba.fastjson.JSON;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/9
 */
public class TestJson {
    @Test
    public void testJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "kevin");
        Date value = new java.sql.Date(new java.util.Date().getTime());
        map.put("date", value);
        System.out.println(JSON.toJSONString(map));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        System.out.println(simpleDateFormat.format(value));
//        System.out.println(JSON.toJSONString(map, new ValueFilter() {
//            @Override
//            public Object process(Object object, String propertyName, Object propertyValue) {
//                if (propertyValue instanceof Date) {
//                    return new DateTime(propertyValue).toString("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
//                } else {
//                    return propertyValue;
//                }
//            }
//        }));
    }

    @Test
    public void testFormat() {
        Date currentDate = new Date();
        System.out.println(new DateTime(currentDate).toString("yyyy-MM-dd hh:mm:ss"));
        System.out.println(new DateTime(currentDate).toDateTimeISO().toString("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
        System.out.println(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
    }

}
