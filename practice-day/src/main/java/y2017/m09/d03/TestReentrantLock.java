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
    public void testReentrant() {
        ReentrantLock lock = new ReentrantLock();
        // 其他线程没有独占，则当前线程会hold该锁
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
    public void testReentrant2() {
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
                    // 如果next为null即最后一个节点，或者next.waitStatus > 1取消状态，需要进行一次遍历查询出waitStatus<0的非当前节点
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
                // 整个过程中当前节点的waitStatus为0

                // 当占有lock的线程，unlock的时候，会遍历队列找到waitStatus<0的head，
                // 将head的next进行LockSupport.unpark
                // 如果next为null即最后一个节点，或者next.waitStatus > 1取消状态，需要进行一次遍历查询出waitStatus<0的非当前节点
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
     * ReentrantLock进行的LockSupport的park操作都是都是以当前线程为第一参数操作的，
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
}

