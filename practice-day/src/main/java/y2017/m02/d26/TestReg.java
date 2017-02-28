package y2017.m02.d26;

import cn.bidlink.framework.core.utils.json.jackson.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2017/2/26
 */
public class TestReg {
    @Test
    public void testReg() {
        String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729";
        Pattern iePattern = Pattern.compile("MSIE\\s+(\\d+\\.\\d+)");
        Matcher matcher = iePattern.matcher(userAgent);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }
    }

    @Test
    public void testJson() {
        String jsonStr = "{\"bidPrice\":{\"name\":\"开标价格(万元/人民币)\",\"data\":{\"1113172334\":11.000000,\"1113172336\":12.000000,\"1113172067\":13.000000}},\"result\":[{\"id\":\"1113172334\",\"name\":\"供应商--悦招测试登录问题\",\"sumPrice\":219.32},{\"id\":\"1113172336\",\"name\":\"投标商--悦招测试登录问题\",\"sumPrice\":200.89},{\"id\":\"1113172067\",\"name\":\"供应商视图测试账号的用户\",\"sumPrice\":120}],\"order\":{\"name\":\"综合排序\",\"data\":{\"1113172334\":1,\"1113172336\":2,\"1113172067\":3}},\"items\":[{\"expertId\":\"15907fce40c442b4a452c7e7dc8853ce\",\"expertName\":\"df\",\"data\":{\"1113172334\":\"77.99\",\"1113172336\":\"48.12\",\"1113172067\":\"5\"}},{\"expertId\":\"4925f64a34154288a73ac4da0b26be7a\",\"expertName\":\"testab\",\"data\":{\"1113172334\":\"74.11\",\"1113172336\":\"76.22\",\"1113172067\":\"55\"}},{\"expertId\":\"3dad97c9201340cba668081dcb18bc96\",\"expertName\":\"testaab\",\"data\":{\"1113172334\":\"67.22\",\"1113172336\":\"76.55\",\"1113172067\":\"60\"}}],\"weightedPrice\":{\"name\":\"加权平均得分\",\"data\":{\"1113172334\":73.11,\"1113172336\":66.96,\"1113172067\":40}},\"sumPrice\":{\"name\":\"合计得分\",\"data\":{\"1113172334\":219.32,\"1113172336\":200.89,\"1113172067\":120}}}";
        Map<String, Object> templateJsonMap = JsonUtils.json2Object(jsonStr, Map.class, null);
        System.out.println(templateJsonMap);

    }

    @Test
    public void testNumber() {
        System.out.println(4.0000 == 4);
    }

    @Test
    public void testJsonArray() {
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        object1.put("test1", "test2");
        JSONObject object2 = new JSONObject();
        object2.put("test3", "test2");
        object.put("TEST", object1);
        object.put("TEST", object2);
        System.out.println(object);
    }

    @Test
    public void testThrowable() {
        try {
            String s = null;
            s.toString();
        } catch (NullPointerException ex) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            ex.printStackTrace(writer);
            System.out.println(stringWriter.getBuffer().toString());
            ex.printStackTrace();
        }
    }
}
