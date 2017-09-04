package y2017.m09.d03;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/4
 */
public class TestReentrantReadWriteLock {
    @Test
    /**
     * 读写锁使用32位的Integer设计，高位16个用于读锁，低位16个用于写锁。
     * 读锁每次+1，需要加2^16；写锁每次+1就好了
     * 所以，读锁的最大个数为65535，写锁的最大个数为65535
     */
    public void testReentrantReadWriteLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // 读锁是共享锁
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        // 写锁是独占锁
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        readLock.lock();
        try {
            System.out.println("read lock");
        } finally {
            readLock.unlock();
        }
    }

    @Test
    public void testReadLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // 读锁
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                try {
                    System.out.println("readLock :" + Thread.currentThread().getName());
                } finally {
                    readLock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readLock.unlock();

    }
}
