package y2017.m11.d29;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/25
 */
public class SyncTimeUtil {
    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String SYNC_TIME = "syncTime";

    public static Timestamp GMT_TIME = new Timestamp(0);

    private static ThreadLocal<Timestamp> currentDate = new InheritableThreadLocal<>();

    public static void setCurrentDate() {
        currentDate.set(new Timestamp(new DateTime().getMillis()));
    }

    public static void setDate(Date date) {
        setDate(date.getTime());
    }

    public static void setDate(long timestamp) {
        currentDate.set(new Timestamp(timestamp));
    }


    public static Timestamp getCurrentDate() {
        return currentDate.get();
    }

    public static String toDateString(Object propertyValue) {
        if (propertyValue instanceof Date) {
            return new DateTime(propertyValue).toString(SyncTimeUtil.DATE_TIME_PATTERN);
        } else {
            return null;
        }
    }
}
