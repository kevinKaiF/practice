//package y2017.m05.d22;
//
//import org.junit.Test;
//
//import java.util.PriorityQueue;
//import java.util.Queue;
//
///**
// * @author : kevin
// * @version : Ver 1.0
// * @description :
// * @date : 2017/5/22
// */
//public class TestPriorityArray {
//    @Test
//    public void testPriorityArray() {
//        Queue<Long> queue = new PriorityQueue<>(8, (o1, o2) -> -o1.compareTo(o2));
//        queue.add(20L);
//        queue.add(12L);
//        queue.add(3L);
//        queue.add(18L);
//        queue.add(1L);
//        queue.add(4L);
//        queue.add(38L);
//        queue.add(8L);
//
//        System.out.println(queue);
//
//        queue.stream().forEach(s -> System.out.println(s));
//    }
//}
