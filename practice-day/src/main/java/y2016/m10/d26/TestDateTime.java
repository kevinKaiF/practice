package y2016.m10.d26;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;


/**
 * @description:
 * @version: 1.0
 * 2016/10/26
 */
public class TestDateTime {
    @Test
    public void testDateTime() {
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2016-09-22");
        System.out.println(dateTime);
    }
}
