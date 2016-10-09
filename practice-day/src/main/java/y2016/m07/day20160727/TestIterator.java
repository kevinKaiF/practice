package y2016.m07.day20160727;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-27 PM01:26
 */
public class TestIterator {
    @Test
    public void testIterator() {
        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("test1");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if("test1".equals(temp)) {
                iterator.remove();
            }
        }

        System.out.println(list.size());
    }

    @Test
    public void testChar() {
        char c = '\001';
        System.out.println("\t".toCharArray()[0]);
        System.out.println("\t".toCharArray()[0] == c);
        System.out.println(c);
        System.out.println(' ' == c);
    }

    @Test
    public void testStrip() {
        System.out.println(StringUtils.strip(" 2 2  "));;
    }

}
