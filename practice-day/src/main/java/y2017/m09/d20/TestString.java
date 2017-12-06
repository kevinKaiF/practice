package y2017.m09.d20;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import y2016.m05.day20160520.JsonUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/20
 */
public class TestString {
    private static ThreadLocal<Map> threadLocal = new ThreadLocal<Map>() {
        @Override
        protected Map initialValue() {
            return new HashMap();
        }
    };
    @Test
    public void testReplace() {
        String str = "test$^&2313";
        System.out.println(str.replace("$^&", ""));
    }

    @Test
    public void testJson() {
        Map projectInfoJson = new LinkedHashMap<>();
        projectInfoJson.put("招标项目编号", 2222);
        projectInfoJson.put("项目名称", "test");
        projectInfoJson.put("招标人", "22");
        projectInfoJson.put("招标方式", "公开招标");
        projectInfoJson.put("投标报价方式", "线上投标");
        projectInfoJson.put("资格预审文件领购开始时间", new DateTime(new Date()).toString("yyyy-MM-dd HH:mm"));
        System.out.println(projectInfoJson.toString());

        HashMap<String, Object> proInfoMap = JsonUtils.json2Object(JsonUtils.toJson(projectInfoJson, null, null, null), LinkedHashMap.class, "yyyy-MM-dd HH:mm:ss");
        for (String s : proInfoMap.keySet()) {
            System.out.println(s + ":" + proInfoMap.get(s));
        }

    }

    @Test
    public void testDivide() {
//        System.out.println(4 / 2 + 1);
        String s = "维生素C，水果";
        System.out.println(split(s));

        long millis = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2017-01-01").getMillis();
        System.out.println(millis);
    }

    @Test
    public void testTimestamp() {
        Timestamp timestamp = new Timestamp(0);
        System.out.println(timestamp);
        DateTime dateTime = new DateTime(new Date());
        long parseMillis = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseMillis("2017-09-22 17:00:34");
        System.out.println(parseMillis);
    }

    public String split(String directoryName) {
        return Arrays.toString(directoryName.replaceAll("[,;.，。、\\s\\t]", ",").split(","));
    }

    @Test
    public void testLogger() {
        System.setProperty("log4j.debug", "true");
        // Log4jLoggerFactory实现，由org.apache.log4j.LogManager生成Logger
        Logger logger = LoggerFactory.getLogger(TestString.class);
        // Log4jLoggerAdapter由适配器，实际上org.apache.log4j.Logger来处理
        // 调用所有的Appender，输出日志
        logger.info("test");
    }

    @Test
    public void testNull() {
//        System.out.println(null instanceof Object);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            list.add(i + "");
        }

        for (int i = 0; i < list.size()/ 10 + 1; i++) {
            List<String> strings = list.subList(i * 10, Math.min(i * 10 + 10, list.size()));
            System.out.println(strings);
        }
    }

    @Test
    public void testThreadLocal() {
        Map map = threadLocal.get();
        map.put("test", 1);
        System.out.println(threadLocal.get());
        System.out.println((byte)128);
    }

    @Test
    public void testMessageFormat() {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat("test {}, te {}", new Object[]{2, 3});
        System.out.println(formattingTuple.getMessage());
        String s = "{";
        System.out.println('\\' == 92);
    }

    @Test
    public void testGetPackagePath() {
        // 获取该class目录所在的文件夹
        System.out.println(TestString.class.getProtectionDomain().getCodeSource().getLocation());
    }


}
