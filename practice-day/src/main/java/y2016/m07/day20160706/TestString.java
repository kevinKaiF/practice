package y2016.m07.day20160706;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The type Test string.
 *
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-06 AM09:05
 */
public class TestString {
    /**
     * format支持null.
     *
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-06 09:52:55
     */
    @Test
    public void testStringFormat() {
        String s = null;
        System.out.println(String.format("%s_%s", s, s));
    }

    @Test
    public void testListSkipList() {
        Set<String> list = new ConcurrentSkipListSet<>();
        list.add("222");
        list.add("221");
        list.add("3333");
        System.out.println(list);
    }

    @Test
    public void testMessageFormat() {
        System.out.println(MessageFormat.format("{0}-{1}", "kevin", "age"));
    }
}
