package y2016.m06.day20160602;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-02 AM11:04
 */
public class TestCollection {
    @Test
    public void testSort() {
        List<String> list = new ArrayList<>();
        list.add("c");
        list.add("b");
        list.add("a");

        // 在底层排序中，归并算法会使用compareTo来进行比较
        Collections.sort(list);

        System.out.println(list.toString());

    }

    @Test
    public void testHashTable() {
//        Hashtable
    }

    @Test
    public void testClone() {
        int[] arr = {1, 2};
        int[] copy = arr.clone();
        System.out.println(arr == copy);
        System.out.println(copy.length);
        System.out.println(copy[0]);

    }

    @Test
    public void testBigInteger() {
        BigInteger bigInteger = BigInteger.ONE;
    }

    @Test
    public void testInteger() {
        Integer a = Integer.valueOf(22);
//        String.copyValueOf();
//        String.valueOf()
    }

    @Test
    public void testArrays() {
        // Arrays.asList是通过ArrayList来实现的
        List list = Arrays.asList(new String[] {"11"});
    }
}
