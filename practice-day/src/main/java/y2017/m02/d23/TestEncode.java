package y2017.m02.d23;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-02-23 PM03:53
 */
public class TestEncode {
    @Test
    public void testEncode() throws UnsupportedEncodingException {
        System.out.println(toHexString(' '));
        System.out.println(toHexString('='));
        System.out.println("□");
        System.out.println(toHexString('□'));
        System.out.println(toHexString('□'));
    }

    private String toHexString(char chr) throws UnsupportedEncodingException {
        byte[] bytes = String.valueOf(chr).getBytes("UTF-8");
        System.out.println(Arrays.toString(bytes));
        return "%" + Integer.toHexString(chr);
    }

    @Test
    public void testLinkedHashMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("test1", "test1");
        map.put("test3", "test3");
        map.put("test4", "test4");
        map.put("test2", "test2");
//        System.out.println(map.toString());

        Set<Map.Entry<String, String>> entries = map.entrySet();
        System.out.println(entries);
    }
}
