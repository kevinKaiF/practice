package y2016.m09.d30;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-30 PM01:08
 */
public class TestJoda {
    @Test
    public void testJoda() {

        long yesterday = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2016-09-29 13:34:00").getMillis();
        long millis = new DateTime().plus(-yesterday).getMillis();
        System.out.println(millis - 24 * 3600 * 1000);

        DateTime dateTime = new DateTime(System.currentTimeMillis());
        System.out.println(dateTime.toString("yyyyMMdd"));
    }
}
