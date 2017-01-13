package y2016.m11.d30;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/30
 */
public class CLHLock {
    private ThreadLocal<Node> pred;
    private ThreadLocal<Node> node;     // node共享
    private List<Node> nodeList = new ArrayList<>();
    private CountDownLatch countDownLatch = new CountDownLatch(3);
    // 初始tail的状态为false
    private AtomicReference<Node> tail = new AtomicReference<>(new Node());

    public CLHLock() {
        pred = new ThreadLocal<Node>() {
            @Override
            protected Node initialValue() {
                return null;
            }
        };

        node = new ThreadLocal<Node>() {
            @Override
            protected Node initialValue() {
                return new Node();
            }
        };

    }

    public void lock() {
        /**
         * 这里每个线程node.get()获取的值是不同的，因为每个线程都会执行initValue这个方法
         */
        final Node node = this.node.get();  // 取出当前节点的状态
        node.locked = true;
        /**
         * 1.这个也是非常关键的，依次获取上个lock线程的值，并重新set当前线程的值，tail类似隐形的队列
         * 2.由于node每个线程首次获取都是崭新的，所以pred的lock始终是false
         */
        Node pred = tail.getAndSet(node);   // 取出tail初始状态
        /**
         * pred的目的只是保存引用，优化jvm避免重复创建新的对象
         */
      //  this.pred.set(pred);                // 保存tail的初始状态到pred
        nodeList.add(node);
        while (pred.locked) {
            // 这里可以优化为sleep，降低CPU的占用率
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }              // 自旋（spin）,首次加锁的时候，无需自旋，之后lock的线程必须加锁等待
    }

    public void unlock() {
        final Node node = this.node.get();  // 取出当前节点的状态
        node.locked = false;                // 设置为false，即非锁定状态
//        tail.set(new Node());
        /**
         * 优化jvm的内存使用，避免创建重复的对象
         */
        tail.set(this.pred.get());          // 将pred保存的原始状态，重置到tail中
    }



    private class Node {
        private volatile boolean locked;
    }

    public static void main(String[] args) throws InterruptedException {
        final CLHLock lock = new CLHLock();
        Thread t = new Thread() {
            public void run(){
                lock.lock();                    // 第一个lock的线程可以直接运行，其他线程处于spin状态，pred.lock=true
                try {
                    lock.countDownLatch.countDown();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };

        t.start();

        Thread.sleep(10);

        Thread t1 = new Thread() {
            public void run(){
                lock.lock();
                lock.countDownLatch.countDown();
                System.out.println("first.");  // 由于pred.lock=true，所以自旋等待
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };

        t1.start();
        Thread.sleep(10);

        Thread t2 = new Thread() {
            public void run(){
                lock.lock();
                lock.countDownLatch.countDown();
                System.out.println("second.");
                lock.unlock();
            }
        };

        t2.start();

        lock.countDownLatch.await();
        System.out.println(lock.nodeList);
        System.out.println(lock.nodeList.get(0) == lock.nodeList.get(1));
        System.out.println(lock.nodeList.get(0) == lock.nodeList.get(2));
        System.out.println(lock.nodeList.get(1) == lock.nodeList.get(2));

    }
}
