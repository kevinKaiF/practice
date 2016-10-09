package y2016.m08.day20160826;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-26 PM02:26
 */
public class HexUtil {
    public static String str2Hex(String str) throws UnsupportedEncodingException {
        String hexRaw = String.format("%x", new BigInteger(1, str.getBytes("UTF-8")));
        char[] hexRawArr = hexRaw.toCharArray();
        StringBuilder hexFmtStr = new StringBuilder();
        final String SEP = "\\x";
        for (int i = 0; i < hexRawArr.length; i++) {
            hexFmtStr.append(SEP).append(hexRawArr[i]).append(hexRawArr[++i]);
        }
        return hexFmtStr.toString();
    }

    public static String hex2Str(String str) throws UnsupportedEncodingException {
        String strArr[] = str.split("\\\\"); // 分割拿到形如 xE9 的16进制数据
        byte[] byteArr = new byte[strArr.length - 1];
        for (int i = 1; i < strArr.length; i++) {
            Integer hexInt = Integer.decode("0" + strArr[i]);
            byteArr[i - 1] = hexInt.byteValue();
        }

        return new String(byteArr, "UTF-8");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String companyName = "\\xE4\\xB8\\x8A\\xE6\\xB5\\xB7\\xE9\\x98\\xB3\\xE4\\xB8\\x80\\xE9\\x98\\x80\\xE9\\x97\\xA8\\xE6\\x9C\\x89\\xE9\\x99\\x90\\xE5\\x85\\xAC\\xE5\\x8F\\xB8";
        String companyName = "\\x5c";
        System.out.println(hex2Str(companyName));
    }

    @Test
    public void testIntegerDecode() {
        System.out.println(Integer.decode("0xFF"));
    }

}
