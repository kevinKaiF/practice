package y2017.m03.d24;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/24
 */
public class TestJson {
    @Test
    public void testJson() {
        String jsonStr = "[\n" +
                "    {\n" +
                "        \"placeName\": \"安徽\",\n" +
                "        \"placeNumber\": \"340000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"北京\",\n" +
                "        \"placeNumber\": \"110000\",\n" +
                "        \"placeHot\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"福建\",\n" +
                "        \"placeNumber\": \"350000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"甘肃\",\n" +
                "        \"placeNumber\": \"620000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"广东\",\n" +
                "        \"placeNumber\": \"440000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"贵州\",\n" +
                "        \"placeNumber\": \"520000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"海南\",\n" +
                "        \"placeNumber\": \"460000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"河北\",\n" +
                "        \"placeNumber\": \"130000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"河南\",\n" +
                "        \"placeNumber\": \"410000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"湖北\",\n" +
                "        \"placeNumber\": \"420000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"湖南\",\n" +
                "        \"placeNumber\": \"430000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"吉林\",\n" +
                "        \"placeNumber\": \"220000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"江苏\",\n" +
                "        \"placeNumber\": \"320000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"江西\",\n" +
                "        \"placeNumber\": \"360000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"辽宁\",\n" +
                "        \"placeNumber\": \"210000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"青海\",\n" +
                "        \"placeNumber\": \"630000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"山东\",\n" +
                "        \"placeNumber\": \"370000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"山西\",\n" +
                "        \"placeNumber\": \"140000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"陕西\",\n" +
                "        \"placeNumber\": \"610000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"上海\",\n" +
                "        \"placeNumber\": \"310000\",\n" +
                "        \"placeHot\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"四川\",\n" +
                "        \"placeNumber\": \"510000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"天津\",\n" +
                "        \"placeNumber\": \"120000\",\n" +
                "        \"placeHot\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"云南\",\n" +
                "        \"placeNumber\": \"530000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"浙江\",\n" +
                "        \"placeNumber\": \"330000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"重庆\",\n" +
                "        \"placeNumber\": \"500000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"黑龙江\",\n" +
                "        \"placeNumber\": \"230000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"西藏自治区\",\n" +
                "        \"placeNumber\": \"540000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"新疆维吾尔自治区\",\n" +
                "        \"placeNumber\": \"650000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"广西壮族自治区\",\n" +
                "        \"placeNumber\": \"450000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"内蒙古自治区\",\n" +
                "        \"placeNumber\": \"150000\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"placeName\": \"宁夏回族自治区\",\n" +
                "        \"placeNumber\": \"640000\"\n" +
                "    }\n" +
                "]";
        JSONArray jsonArray = JSONObject.parseArray(jsonStr);
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonO = jsonArray.getJSONObject(i);
            jsonObject.put(jsonO.getString("placeNumber"), jsonO.getString("placeName"));
        }

        System.out.println(jsonObject.toString());
    }

    @Test
    public void testJson2() {
        String jsonStr = "{\"620000\":\"甘肃\",\"370000\":\"山东\",\"320000\":\"江苏\",\"110000\":\"北京\",\"530000\":\"云南\",\"460000\":\"海南\",\"330000\":\"浙江\",\"310000\":\"上海\",\"610000\":\"陕西\",\"120000\":\"天津\",\"650000\":\"新疆维吾尔自治区\",\"520000\":\"贵州\",\"340000\":\"安徽\",\"430000\":\"湖南\",\"130000\":\"河北\",\"210000\":\"辽宁\",\"510000\":\"四川\",\"640000\":\"宁夏回族自治区\",\"220000\":\"吉林\",\"350000\":\"福建\",\"420000\":\"湖北\",\"440000\":\"广东\",\"500000\":\"重庆\",\"140000\":\"山西\",\"360000\":\"江西\",\"230000\":\"黑龙江\",\"630000\":\"青海\",\"410000\":\"河南\",\"150000\":\"内蒙古自治区\",\"540000\":\"西藏自治区\",\"450000\":\"广西壮族自治区\"}";
        JSONObject parse = JSONObject.parseObject(jsonStr);
        System.out.println(parse.get("620000"));
    }
}
