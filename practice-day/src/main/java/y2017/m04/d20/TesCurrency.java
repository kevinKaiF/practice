package y2017.m04.d20;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/20
 */
public class TesCurrency {
    @Test
    public void testCurrency() {
        Currency rmb = Currency.getInstance(Locale.CHINA);
        String displayName = rmb.getDisplayName();
        System.out.println(displayName);

        NumberFormat instance = NumberFormat.getCurrencyInstance(Locale.CHINA);
        NumberFormat instance1 = NumberFormat.getCurrencyInstance(Locale.US);

        String format = instance.format(22.1);
        System.out.println(format);
        String format1 = instance1.format(22.1);
        System.out.println(format1);

        char c = '￥';
        System.out.println(((int) c));
    }

}
