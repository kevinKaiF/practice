package y2017.m08.d10;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/10
 */
public class TestByte {
    @Test
    public void testByte() {
        System.out.println((byte) 127);  // 01111111 8F
        System.out.println((byte) 254);
        System.out.println((byte) 255);
        System.out.println((byte) 256);
    }

    @Test
    public void testMd5() {
        String name = "kevin";
        System.out.println(Md5Encoder.encode(name.getBytes()));
    }

    @Test
    public void testDateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(currentTimeMillis);
        java.util.Date utilDate = new java.util.Date(currentTimeMillis);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(currentTimeMillis);

        System.out.println(new DateTime(sqlDate).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println(new DateTime(utilDate).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println(new DateTime(timestamp).toString("yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void testLong() {
        long a = 1218258340086284723L;
        System.out.println(a);
    }

}
