package y2016.m08.day20160803;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-03 PM02:41
 */
public class TestReentrantReadWriteLock {
    @Test
    public void testReentrantReadWriteLock() throws InterruptedException {

        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.readLock().lock();
                try {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("lock 1");
                } finally {
                    lock.readLock().unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.readLock().lock();
                try {
                    System.out.println("lock 2");
                } finally {
                    lock.readLock().unlock();
                }
            }
        });


        thread1.start();
        thread2.start();

        Thread.currentThread().join(0);
    }

    /**
     * writeLock是互斥锁，只能有一个线程hold writeLock.
     *
     * @throws InterruptedException the interrupted exception
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-03 15:06:42
     */
    @Test
    public void testReentrantReadWriteLock2() throws InterruptedException {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                try {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("lock 1");
                } finally {
                    lock.writeLock().unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                try {
                    System.out.println("lock 2");
                } finally {
                    lock.writeLock().unlock();
                }
            }
        });

        thread1.start();
        thread2.start();
        lock.readLock();

        Thread.currentThread().join(0);
    }
}
