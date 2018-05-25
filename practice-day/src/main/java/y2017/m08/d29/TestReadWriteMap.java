//package y2017.m08.d29;
//
//import org.junit.Test;
//
///**
// * @author : kevin
// * @version : Ver 1.0
// * @description :
// * @date : 2017/8/29
// */
//public class TestReadWriteMap {
//    @Test
//    public void test1() {
//        ReadWriteMap<String, Object> map = new ReadWriteMap<>();
//        map.put("test1", 2);
//        map.put("test2", 3);
//        map.put("test3", 4);
//        map.put("test4", 5);
////        for (Map.Entry<String, Object> entry : map.entrySet()) {
////            System.out.println(entry);
////        }
////        System.out.println(map);
//        Thread thread = new Thread(() -> {
//            System.out.println(map.get("test1"));
//        });
//        thread.setName("test");
//        thread.start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(map.get("test1"));
//        }).start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(map.get("test1"));
//        }).start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
