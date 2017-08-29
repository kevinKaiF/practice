package y2017.m03.d13;

import org.joda.time.DateTime;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/13
 */
public class TestDate {
    @Test
    public void testDate() {
        System.out.println(new DateTime().toString("yyyy-MM-dd HH:mm"));
    }

    @Test
    public void testInetAddress() throws UnknownHostException {
        byte[] bytes = new byte[] {(byte) 200, (byte) 181, 111, (byte) 188};
        InetAddress byName = InetAddress.getByAddress(bytes);
        System.out.println(byName);
    }

    @Test
    public void testLocation() {
        System.out.println(TestDate.class.getProtectionDomain().getCodeSource().getLocation());
    }
}
