package y2017.m06.d06;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/6
 */
public class TestException {
    @Test
    public void testException() {
        try {
            String s = null;
            s.toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ExceptionUtils.getFullStackTrace(e));
        }

    }

    @Test
    public void testList() {
        List<String> list = Collections.EMPTY_LIST;
        System.out.println(list);
    }

    @Test
    public void testJson() {
        Object[] objects = {new HashMap<>(), 1};
        System.out.println(JSON.toJSON(objects));
     }
}
