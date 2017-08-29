package y2017.m03.d27;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/27
 */
public class TestScript {
    @Test
    public void testScript() {
        Main main = new Main();
        main.main(new String[]{"-l", "js"});
    }

    @Test
    public void testDouble() {
        System.out.println((1033.2444 / 10));
    }
}
