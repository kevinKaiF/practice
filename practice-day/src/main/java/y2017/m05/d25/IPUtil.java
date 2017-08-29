package y2017.m05.d25;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/25
 */
public class IPUtil {
    public static int valueOf(String ipStr) {
        String[] strArr = ipStr.split("\\.");
        int num = 0;
        num |= Integer.valueOf(strArr[0]) << 24;
        num |= Integer.valueOf(strArr[1]) << 16;
        num |= Integer.valueOf(strArr[2]) << 8;
        num |= Integer.valueOf(strArr[3]) << 0;
        return num;
    }

    public static String toString (int num) {
        StringBuilder sb = new StringBuilder();
        sb.append(num >>> 24 & 0x00FF).append(".");
        sb.append(num >>> 16 & 0x00FF).append(".");
        sb.append(num >>> 8 & 0x00FF).append(".");
        sb.append(num >>> 0 & 0x00FF);
        return sb.toString();
    }

    @Test
    public void testValueOf() {
        int num = valueOf("122.22.33.254");
        System.out.println(num);
        System.out.println(toString(num));
    }
}
