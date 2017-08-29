package y2017.m05.d09;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/9
 */
public class TestDateTime {
    @Test
    public void testDateTime() {
        Date currentTime = new Date();
        currentTime = DateUtils.setHours(currentTime, 0);
        currentTime = DateUtils.setMinutes(currentTime, 0);
        currentTime = DateUtils.setSeconds(currentTime, 0);
        System.out.println(currentTime);
        Date date = DateUtils.addDays(currentTime, -1);
        System.out.println(date);
    }
}
