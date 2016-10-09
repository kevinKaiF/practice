package y2016.m08.day20160803;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-03 AM10:55
 */
public class TestReentrantLock {
    /**
     * reentrant 可重入的体现：
     * 持有该锁的当前线程中，可以多次获取锁，即多次lock,tryLock。
     * 获取锁的数目是用int volatile state来表示。
     * 由于state是int变量，可重入的数目是上限的Integer.MAX_VALUE，如果溢出会抛出Error:Maximum lock count exceeded
     *
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-03 11:50:47
     */
    @Test
    public void testReentrantLock() {
        final ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println(lock.tryLock());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("require");
                System.out.println(lock.getHoldCount());
                try {
                    System.out.println("run once!");
                } finally {
                    System.out.println("release");
                    lock.unlock();
                    System.out.println(lock.getHoldCount());
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(lock.tryLock()) {
//                    lock.lock();
                    System.out.println("require2");
                    System.out.println(lock.getHoldCount());
                    try {
                        System.out.println("run once2!");
                    } finally {
                        System.out.println("release2");
                        lock.unlock();
                        System.out.println(lock.getHoldCount());
                    }
                } else {
                    System.out.println("acquire failed!");
                }
            }
        });

        thread.start();
        thread1.start();

        try {
            Thread.currentThread().join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReentrantLock2() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {
            System.out.println("test");
            System.out.println(lock.isHeldByCurrentThread());
        } finally {
            lock.unlock();
        }

    }
}
