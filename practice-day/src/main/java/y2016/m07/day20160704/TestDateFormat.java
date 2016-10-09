package y2016.m07.day20160704;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-04 AM10:18
 */
public class TestDateFormat {
    @Test
    public void testDateFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date date = sdf.parse("2015-06-01 10:22");
        Date date = sdf.parse("2015-06-01");
        System.out.println(date);
    }

    @Test
    public void testSplit() {
        String s = "2    2";
        System.out.println(s.split(" ").length);
        System.out.println(split(s, " ").length);
    }

    private String[] split(String s, String separatorChars) {
        if(s == null) {
            return null;
        }

        String[] arr = s.split(separatorChars);
        List<String> list = new ArrayList<>();
        for(String item : arr) {
            if(!"".equals(item)) {
                list.add(item);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * StringUtils的split与String的split方法是有差异的！！！.
     *
     * @author : kevin
     * @date : 2016-07-04 17:49:10
     */
    @Test
    public void testStringUtil() {
        String s = "2    2";
        System.out.println(StringUtils.split(s, " ").length);
    }
}
