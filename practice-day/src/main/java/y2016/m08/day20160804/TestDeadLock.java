package y2016.m08.day20160804;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-04 PM03:29
 */
public class TestDeadLock {
    final Object lock1 = new Object();
    final Object lock2 = new Object();
    @Test
    public void testDeadLock() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {
                        System.out.println("t1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock1) {
                        System.out.println("t2");
                    }
                }
            }
        });

//        t1.start();
//        t2.start();
//
//        Thread.sleep(4000);
        System.out.println(Thread.activeCount());
        Thread.currentThread().join(0);
    }

}
