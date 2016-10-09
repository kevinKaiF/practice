package y2016.m04.day20160425;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-04-25 AM09:52
 */
public class CharTest {
    public static void main(String[] args) {
        System.out.println((char)36);
        System.out.println((int)'1');
        byte b = (byte)3000;
        System.out.println(Integer.toBinaryString(3000));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(b & 0xff);
        System.out.println((int)b);
    }
}
