package y2016.m05.day20160506;

import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-06 AM10:42
 */
public class ObjectArray {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<String>(){
            {
                add("sss");
                add("sss");
                add("sss");
                add("sss");
            }
        };

        List<Integer> integers = new ArrayList<Integer>() {
            {
                add(1);
            }
        };
        Object[] objects = new Object[] {stringList, integers};
        List<String> strings = (List<String>) objects[0];
        System.out.println(strings);
    }

    @Test
    public void testSqlDate() {
        Date date = Date.valueOf("2001-10-22");
        System.out.println(date.toString());

        date = Date.valueOf("10-22");
        System.out.println(date.toString());
    }



}
