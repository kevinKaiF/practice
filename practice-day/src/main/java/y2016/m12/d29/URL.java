package y2016.m12.d29;

import java.util.Map;

/**
 * url example:
 * <ul>
 * http://www.facebook.com/friends?param1=value1&amp;param2=value2
 * http://username:password@10.20.130.230:8080/list?version=1.0.0
 * <li>ftp://username:password@192.168.1.7:21/1/read.txt
 * <li>registry://192.168.1.7:9090/com.alibaba.service1?param1=value1&amp;param2=value2
 * </ul>
 * <p>
 * Some strange example below:
 * <ul>
 * <li>192.168.1.3:20880<br>
 * for this case, url protocol = null, url host = 192.168.1.3, port = 20880, url path = null
 * <li>file:///home/user1/router.js?type=script<br>
 * for this case, url protocol = null, url host = null, url path = home/user1/router.js
 * <li>file://home/user1/router.js?type=script<br>
 * for this case, url protocol = file, url host = home, url path = user1/router.js
 * <p>
 * <li>file:///D:/1/router.js?type=script<br>
 * for this case, url protocol = file, url host = null, url path = D:/1/router.js
 * <li>file:/D:/1/router.js?type=script<br>
 * same as above file:///D:/1/router.js?type=script
 * <li>/home/user1/router.js?type=script <br>
 * for this case, url protocol = null, url host = null, url path = home/user1/router.js
 * <li>home/user1/router.js?type=script <br>
 * for this case, url protocol = null, url host = home, url path = user1/router.js
 * </ul>
 *
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/29
 */
public class URL {
    private static URLResolver urlResolver = new URLResolver();

    public static Map<String, Object> valueOf(String url) {
        return urlResolver.resolver(url);
    }


    public static void main(String[] args) {
        String[] urls = new String[]{
                "http://www.facebook.com/friends?param1=value1&amp;param2=value2&amp;",
                "http://username:password@10.20.130.230:8080/list?version=1.0.0",
                "ftp://username:password@192.168.1.7:21/1/read.txt",
                "registry://192.168.1.7:9090/com.alibaba.service1?param1=value1&amp;param2=value2",
                "192.168.1.3:20880",
                "file:///home/user1/router.js?type=script",
                "file://home/user1/router.js?type=script",
                "file:///D:/1/router.js?type=script",
                "file:/D:/1/router.js?type=script",
                "/home/user1/router.js?type=script",
                "home/user1/router.js?type=script"
        };

        for (String url : urls) {
            System.out.println(URL.valueOf(url));
        }


    }
}
