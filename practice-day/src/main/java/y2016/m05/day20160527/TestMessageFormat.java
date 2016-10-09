package y2016.m05.day20160527;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-27 PM03:15
 */
public class TestMessageFormat {
    @Test
    public void testMessageFormat() {
        System.out.println(MessageFormat.format("{0} is OK", "香米"));
    }
}
