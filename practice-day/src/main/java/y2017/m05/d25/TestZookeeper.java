//package y2017.m05.d25;
//
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//import java.util.concurrent.CountDownLatch;
//
///**
// * @author : kevin
// * @version : Ver 1.0
// * @description :
// * @date : 2017/5/25
// */
//public class TestZookeeper {
//
//    public void initZookeeper() throws IOException, InterruptedException, KeeperException {
//        String host = "127.0.0.1:2181";
//        int SESSION_TIMEOUT = 5000;
//        CountDownLatch connectSignal = new CountDownLatch(1);
//        ZooKeeper zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, (watchedEvent) -> {
//            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
//                System.out.println(">>> SyncConnected.");
//            }
//            connectSignal.countDown();
//
//            if (watchedEvent.getState() == Watcher.Event.KeeperState.Disconnected) {
//                System.out.println(">>> Disconnected.");
//            }
//        });
//        connectSignal.await();
//        getChildren(zooKeeper);
//    }
//
//    public void getChildren(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
//        Queue<String> pathQueue = new LinkedList<>();
//        List<String> children =  zooKeeper.getChildren("/", false);
//        children.forEach(child -> pathQueue.offer("/" + child));
//        while (pathQueue.size() > 0) {
//            String path = pathQueue.poll();
//            System.out.println(path);
//            children = zooKeeper.getChildren(path, false);
//            if (children.size() > 0) {
//                children.forEach(child -> pathQueue.offer(path + "/" + child));
//            }
//        }
//    }
//
//    @Test
//    public void testZookeeperDisconnected() throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        new Thread(() -> {
//            try {
//                initZookeeper();
//                Thread.sleep(2000);
//                countDownLatch.countDown();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (KeeperException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
////        new Thread(() -> {
////            try {
////                initZookeeper();
////                countDownLatch.countDown();
////            } catch (IOException e) {
////                e.printStackTrace();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (KeeperException e) {
////                e.printStackTrace();
////            }
////        }).start();
////
//        countDownLatch.await();
//    }
//
//
//}
