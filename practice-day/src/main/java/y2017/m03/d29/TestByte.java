package y2017.m03.d29;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/29
 */
public class TestByte {
    @Test
    public void testByte() {
        System.out.println(Integer.toBinaryString(-26).substring(24));
        System.out.println(Integer.toHexString(-26));
        System.out.println(Integer.toHexString(26));
    }

    @Test
    public void testByteUTF8() {
        System.out.println(Integer.toHexString(0xe0 | 0x541b >> 12));
        System.out.println(Integer.toHexString(0x80 | (0x541b >> 6 & 0x3f)));
        System.out.println(Integer.toHexString(0x80 | (0x541b & 0x3f)));
        System.out.println(((char) 127));
    }

    @Test
    public void testGBK() {
        byte b = (byte) 0x541b;
        System.out.println((byte)(0x541b >> 8));
        System.out.println(((byte) 0x541b));
        System.out.println((byte)(48893 >> 8));
        System.out.println(((byte) 48893));
        System.out.println(0x541b & 0xff);
        System.out.println(0x541b);
        System.out.println(0xbe << 8 | 0xfd);
    }

    @Test
    public void testGBK1() {
        //
        System.out.println(Arrays.toString("君".getBytes(Charset.forName("GBK"))));
    }
}
