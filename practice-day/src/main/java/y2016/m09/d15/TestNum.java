package y2016.m09.d15;

import org.junit.Test;


/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM02:09
 */
public class TestNum {
    @Test
    public void testNum() {
        System.out.println(0xFF | 0x00);
        System.out.println(0xFF & 0x00);
        System.out.println(0xFF ^ 0xFF);
        System.out.println(0xFF ^ 0x00);
        System.out.println(~0xFF);
    }

}
