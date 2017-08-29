package y2016.m10.d09;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.Test;

import java.util.Random;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-10-09 AM11:26
 */
public class TestInteger {
    @Test
    public void testInteger() {
        System.out.println(200000 == Integer.valueOf("200000"));
        System.out.println(Integer.valueOf("200000") == Integer.valueOf("200000"));
        System.out.println(Integer.valueOf("128") == Integer.valueOf("128"));
        System.out.println(Integer.valueOf("127") == Integer.valueOf("127"));
    }

    @Test
    public void testJoda() throws InterruptedException {
        DateTime curr = new DateTime();
        Thread.sleep(new Random().nextInt(100));
        DateTime last = new DateTime().plusDays(-2);
        Interval interval = new Interval(last, curr);
        System.out.println(curr.isAfter(last));
        Period period = interval.toPeriod();
        String duration = period.getDays() + "天" + period.getHours() + "时" + period.getMinutes() + "分" + period.getSeconds() + "秒";
        System.out.println(duration);
    }
}
