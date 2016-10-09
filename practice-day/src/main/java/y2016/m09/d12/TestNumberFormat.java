package y2016.m09.d12;

import org.junit.Test;

import java.text.NumberFormat;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-12 PM06:20
 */
public class TestNumberFormat {
    @Test
    public void testNumberFormat() {
        ThreadLocal<NumberFormat> threadLocal = new ThreadLocal<NumberFormat>() {
            @Override
            protected NumberFormat initialValue() {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setGroupingUsed(false); // 默认为true,数字会用逗号隔开
                numberFormat.setMinimumIntegerDigits(5); // 设置整数的位数
                numberFormat.setMinimumFractionDigits(2); // 设置小数的位数
                return numberFormat;
            }
        };

        System.out.println(threadLocal.get().format(100000000.));
    }
}
