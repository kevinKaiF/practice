package y2017.m03.d20;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/20
 */
public class TestSize {
    @Test
    public void testSize() {
        ArrayList<String> arr = new ArrayList<>(Integer.MAX_VALUE / 100);
        arr.add("test");
        Collections.fill(arr, "t");
        System.out.println(arr.size());
        List<String> list = new LinkedList<>(arr);
//        list.addAll(new ArrayList<>(Integer.MAX_VALUE - 2));
        System.out.println(list.size());
    }
}
