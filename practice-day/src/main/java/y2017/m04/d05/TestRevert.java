package y2017.m04.d05;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/1
 */
public class TestRevert {
    @Test
    public void testRevert() {
        // 负数的计算，取反加1
        System.out.println(Integer.toBinaryString(~1));
    }

    @Test
    public void testNegative() {
        System.out.println(Integer.toBinaryString(-16));
        // >>> 负数将最高位置为0
        System.out.println(Integer.toBinaryString(-16 >>> 1));
        System.out.println(Integer.toBinaryString(-16 >> 1));

        System.out.println(Integer.toBinaryString(16));
        System.out.println(Integer.toBinaryString(16 >>> 1));
        System.out.println(Integer.toBinaryString(16 >> 1));
    }

    @Test
    public void testAnd() {
        System.out.println(-1 & 0xffff);
    }

}
