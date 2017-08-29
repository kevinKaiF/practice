package y2017.m08.d17;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.Date;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/17
 */
public class TestDouble {
    @Test
    public void testDouble() {
        double d1 = 1 / 0.0;
        double d2 = 1 / 0.0;
        System.out.println(Math.max(d1, d2));
    }

    @Test
    public void testTimeZone() {
        long currentTimeMillis = 1502989054000L -8 * 3600 * 1000;
        System.out.println(currentTimeMillis);
        long millis = new DateTime(currentTimeMillis).withZone(DateTimeZone.UTC).getMillis();
        System.out.println(millis);
        System.out.println(new DateTime(currentTimeMillis).toString("yyyy-MM-dd HH:mm:ss"));

    }

    @Test
    public void test1() {
        Date correctDate = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2017-08-19 23:00:00").toDate();
        System.out.println(correctDate);
        System.out.println(new DateTime(correctDate).toString("yyyy-MM-dd HH:mm:ss"));
    }

}
