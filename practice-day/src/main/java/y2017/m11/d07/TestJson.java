package y2017.m11.d07;

import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/7
 */
public class TestJson {
    @Test
    public void testJson() {
        InetSocketAddress address = new InetSocketAddress("10.4.0.185", 3306);
        System.out.println(address.getHostString());
    }
}
