package y2017.m09.d03;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kevin
 * @date 2017-09-03 9:24 PM.
 */
public class TestReentrantLock {
    /**
     * 测试独占
     */
    @Test
    public void testSingleThreadUnfair() {
        ReentrantLock lock = new ReentrantLock();
        // 其他线程没有独占，则当前线程会hold该锁
        // 非公平锁在tryAcquire时，当state==0，不会判断队列是否有等待
        lock.lock();
        try {
            // 重入的时候，state只是累加而已，不会将锁请求入队，所以head,tail都是null
            lock.lock();
            System.out.println("locking:" + lock.isHeldByCurrentThread());
            lock.unlock();
        } finally {
            // 解锁的时候会进行release操作，会将state减去释放的个数，
            // 如果当前的state不为0，则exclusiveOwnerThread继续占有当前线程，无法释放锁
            // 如果当前的state为0，则exclusiveOwnerThread为null，独占锁释放
            lock.unlock();
        }
    }

    /**
     * 测试多线程抢占
     */
    @Test
    public void testMultiThreadUnfair() {
        final ReentrantLock lock = new ReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(5000);
                    System.out.println("locking:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 进行tryRelease，判断state=0，即占有锁是否释放
                    // 如果释放了，则对head进行unparkSuccessor操作
                    // 当占有lock的线程，unlock的时候，会遍历队列找到waitStatus<0的head，
                    // 将head的next进行LockSupport.unpark
                    // 如果next为null即最后一个节点，或者next.waitStatus > 0取消状态，需要进行一次遍历查询出waitStatus<0的非当前节点
                    // 然后进行LockSupport.unpark
                    // 这样完成了对等待节点的通知
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果exclusiveOwnerThread占有的线程不是当前线程，则当前线程无法获取锁
                // 同时会加入等待队列，会创建head和tail对象new Node，而head为new Node，当前等待节点为tail
                // head.next == tail tail.prev == head

                // 在acquireQueued中，会尝试tryAcquire一次锁，如果失败则在
                // shouldParkAfterFailedAcquire将head的waitStatus置为-1 signal状态

                // 下次循环则继续尝试tryAcquire一次锁，如果失败则
                // 底层会进行LockSupport.park当前线程
                // 整个过程中当前节点的waitStatus为0，即便在tryAcquire成功的时候，会将当前节点重置为head节点

                // 当占有lock的线程，unlock的时候，会通知head的next，
                // 将head的next进行LockSupport.unpark
                // 如果next为null即最后一个节点，或者next.waitStatus > 0取消状态，需要进行一次遍历查询出waitStatus<0的非当前节点
                // 然后进行LockSupport.unpark

                // 之后acquireQueued继续循环，继续tryAcquire，获取成功后，重新设置head
                // 将当前节点设置为head
                lock.lock();
                try {
                    Thread.sleep(35000);
                    System.out.println("locking:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("locking:" + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    /**
     * ReentrantLock进行的LockSupport的park操作都是都是以当前线程为第一参数操作的，offset都是固定的
     * 所以在park和unpark时候，不同线程互不影响
     */
    public void testLockSupport() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(35000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    /**
     * unsafe进行putObject操作，有点类似map操作，
     * 在进行put get操作时必须保证偏移量的正确性
     */
    public void testUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        Thread t1 = new Thread("t1");
        Thread t2 = new Thread("t2");
        unsafe.putObject(t1, 33L, t2);
        unsafe.putObject(t2, 33L, t1);
        System.out.println(unsafe.getObject(t1, 32L));
        System.out.println(unsafe.getObject(t2, 33L));
    }

    @Test
    public void testSingleThreadFair() {
        ReentrantLock lock = new ReentrantLock(true);
        // 公平锁在tryAcquire的时候，如果state==0,hasQueuedPredecessors会判断队列中是否有等待的节点
        // 如果队列中没有等待节点，head tail均为null。当队列中新加入节点的时候，head和tail都会初始化，head为new Node,tail则为新节点
        // 如果最后一个节点获取到锁，会重置head，并且next为null
        // 如果已经有等待的节点，则head.next.thread不是当前线程
        // 所以hasQueuedPredecessors的判断是 head != tail && ((node = head.next) == null || node.thread != Thread.currentThread)
        lock.lock();
        try {
            // 重入的时候，state只是累加而已，不会将锁请求入队，所以head,tail都是null
            // 公平锁和非公平锁对重入处理是一直的
            lock.lock();
            System.out.println("locking:" + lock.isHeldByCurrentThread());
            lock.unlock();
        } finally {
            // 解锁的时候会进行release操作，会将state减去释放的个数，
            // 如果当前的state不为0，则exclusiveOwnerThread继续占有当前线程，无法释放锁
            // 如果当前的state为0，则exclusiveOwnerThread为null，独占锁释放
            lock.unlock();
        }
    }

    @Test
    public void testMultiThreadFair() {
        final ReentrantLock lock = new ReentrantLock(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(5000);
                    System.out.println("locking:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(35000);
                    System.out.println("locking:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("locking:" + Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

