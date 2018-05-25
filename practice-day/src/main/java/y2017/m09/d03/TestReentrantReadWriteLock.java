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
     * 默认是非公平队列，
     * 写锁获取始终是抢占式的，
     * 读锁获取要判断队列中等待的节点是否是共享模式，
     * 如果是共享模式则readShouldBlock为false，不需要阻塞。
     * 如果是独占模式则readShouldBlock为true，需要阻塞
     *
     * 设置为公平队列，
     * 读锁和写锁的获取都需要判断hasQueuedPredecessors，如果为true说明队列已经有等待，需要进行阻塞，否则不需要
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
            writeLock.unlock();
        }
    }

    // AbstractQueuedSynchronizer 中用state来控制锁的获取
    // 等待队列是Node链表，状态位有CANCEL,SIGNAL,CONDITION,PROPAGATION
    // CANCEL 表示取消等待
    // SIGNAL 表示需要unpark
    // CONDITION
    // PROPAGATION 只在共享模式下，唤醒其他等待的节点

    // Condition对应传统的Object的wait notify notifyAll

    @Test
    public void testReadLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // 读锁和写锁最关键的地方是
        // 1.写锁是按当前线程是否是holder，而读锁不用判断（只是简单判断holder是否已占用，如果已占用，读取就不能再读了）
        // 2.读锁unpark的时候，会进行setHeaderAndPropagation，即会释放其他等待的读锁

        // 读锁lock的时候需要acquireShared。
        // 先进行tryAcquireShared
        // 如果占用写锁的线程不是当前线程，读锁请求tryAcquireShared失败，会doAcquireShared再次尝试获取锁
        // (如果没有独占，读锁可以随意获取了)
        // 如果两次尝试获取失败，则被LockSupport.park阻塞
        // 当前写锁释放了会通知head.next的进行LockSupport.unpark，读锁会继续doAcquireShared。
        // 如果此时读锁获取到了会进行setHeadAndPropagate，不过此时head == node，会直接break。waitStatus==0
        // 如果此时读锁没有获取到则再次LockSupport.park，waitStatus==-1。当再次LockSupport.unpark的时候，读锁会继续doAcquireShared。
        // 如果此时读锁获取到了会进行setHeadAndPropagate。此时的waitStatus==-1，会进行doReleaseShared，唤醒等待的读锁，
        // (如果next节点是写锁，写锁被唤醒一次继续park)
        // （为什么不是写锁呢？因为本案例只有读锁，所以写锁会直接获取到不用等待）。唤醒的锁会继续唤醒等待的读锁，这样就实现了读锁的共享。
        // 首次获取读锁，firstReader为当前线程，并firstReaderHoldCount=1。如果重入的话，firstReaderHoldCount++
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                try {
                    System.out.println("readLock :" + Thread.currentThread().getName());
                } finally {
                    // 读锁unlock的时候需要releaseShared
                    // 先进行tryReleaseShared
                    // 如果firstReader是当前线程，并firstReaderHoldCount==1则释放firstReader，否则就重入则firstReaderHoldCount--
                    // 如果firstReader不是当前线程，则需要将ThreadLocal变量的readHolds中count--。如果小于等于1，直接删除
                    // state减去2^16，原子性更新state，如果state==0，说明读锁已经完全释放了。需要再次doReleaseShared
                    // 否则直接返回false
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

    @Test
    public void testWriteLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        // 写锁是独占锁，tryAcquire获取独占锁，如果exclusiveOwnerThread为null，则成功获取锁。
        // 重入则state+1
        // 否则加入等待队列，节点的模式独占模式。
        // acquireQueued之后呢，会tryAcquire再次尝试获取锁，如果是失败了，则会shouldParkAfterFailedAcquire会将node.prev的waitStatus标记为-1
        // 再次循环，tryAcquire再次尝试获取锁，如果失败了，则LockSupport会park当前线程，进行阻塞状态
        writeLock.lock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeLock.lock();
                try {
                    System.out.println("locking : " + Thread.currentThread().getName());
                } finally {
                    // 独占锁进行release
                    // state-1,原子性更新state，如果state==0，exclusiveOwnerThread置为null。并unparkSuccessor通知等待的节点
                    // 即找到head.next的节点进行LockSupport.unpark
                    // 否则返回false
                    writeLock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        writeLock.unlock();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
