package y2017.m11.d01;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/1
 */
public class TestBigDecimal {
    @Test
    public void testBigDecimal() {
        BigDecimal bigDecimal = new BigDecimal(9876.097);
        System.out.println(new DecimalFormat("0.00").format(bigDecimal));
        BigDecimal bigDecimal1 = new BigDecimal(9876.045);
        System.out.println(new DecimalFormat("0.00").format(bigDecimal1));
        BigDecimal bigDecimal2 = new BigDecimal(9876.034);
        System.out.println(new DecimalFormat("0.00").format(bigDecimal2));
        double v = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(String.format("%f", v));
        double bb = new BigDecimal(33).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        long bb1 = (long) bb;
        Number o = bb;
        if (bb1 == bb) {
            o = bb1;
        }
        System.out.println(o);

        System.out.println(new BigDecimal(300).divide(new BigDecimal(10)));
    }
}
