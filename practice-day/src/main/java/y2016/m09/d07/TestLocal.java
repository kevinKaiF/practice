package y2016.m09.d07;

import org.junit.Test;

import java.net.*;
import java.util.Enumeration;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-07 AM10:57
 */
public class TestLocal {
    @Test
    public void testLocal() {
        System.out.println(getLocalIp());
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testChar() {
        System.out.println(((char) 47));
        System.out.println(((char) 58));
    }



    private String getLocalIp() {
        final String localHostIp = "127.0.0.1";
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(localHostIp.equals(ip)) {
            Enumeration allNetInterfaces = null;
            try {
                allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                InetAddress address = null;
                while (allNetInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        address = (InetAddress) addresses.nextElement();
                        if (address != null && address instanceof Inet4Address) {
                            return address.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else {
            return ip;
        }

        return null;
    }

}
