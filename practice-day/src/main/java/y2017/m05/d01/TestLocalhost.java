package y2017.m05.d01;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/1
 */
public class TestLocalhost {
    @Test
    public void testLocalHost() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostName());
        System.out.println(localHost.getHostAddress());
        String canonicalHostName = localHost.getCanonicalHostName();
        System.out.println(canonicalHostName);

        Runtime runtime = Runtime.getRuntime();
        int MB_BYTES = 1024 * 1024;
        // jvm剩余的内存量
        System.out.println(runtime.freeMemory() / MB_BYTES + "MB");
        // jvm可使用的最大内存量
        System.out.println(runtime.maxMemory() / MB_BYTES + "MB");
        // jvm当前使用的内存量
        System.out.println(runtime.totalMemory() / MB_BYTES + "MB");
    }

    @Test
    public void testChar() {
        System.out.println(((char) 37));
        System.out.println(((int) '%'));
        System.out.println(((int) '\\'));
    }

}
