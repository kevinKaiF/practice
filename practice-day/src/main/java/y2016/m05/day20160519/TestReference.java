package y2016.m05.day20160519;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-19 PM03:21
 */
public class TestReference {

    /**
     * 软引用和弱引用很相似
     * 软引用要比弱引用在内存中存活更长的时间，当内存不足时才回收引用的对象
     */
    @Test
    public void testSoftReference() {
        SoftReference<String> reference = new SoftReference<String>("222");
        System.out.println(reference.get());
    }

    /**
     * 弱引用不能阻止垃圾收集器对对象的回收
     * 只要垃圾收集器扫描到弱引用，就会回收所引用的对象
     */
    @Test
    public void testWeakReference() {
        WeakReference<String> reference = new WeakReference<String>("ns");
        System.out.println(reference.get());
    }

    /**
     * PhantomReference 虚引用
     * 1.其get始终返回的是null，因为虚引用的引用始终是不可访问的,目的是不让标记的对象复活
     * 2.其constructor只有一个带ReferenceQueue的构造
     */
    @Test
    public void testPhantomReference() {
        PhantomReference<String> reference = new PhantomReference<String>(new String(""), new ReferenceQueue<CharSequence>());
        System.out.println(reference.isEnqueued());
    }

    /**
     * AtomicReference仅仅实现了Serializable接口
     * 其底层是通过sun.misc.Unsafe的compareAndSwapObject实现原子性的替换操作
     */
    @Test
    public void testAtomicReference() {
        AtomicReference<String> reference = new AtomicReference<String>();
        reference.set("222");
        System.out.println(reference.get());
    }

    /**
     * 对静态变量加锁，相当于对这个类在整个应用程序中加锁，不论你创建多少个实例
     * @throws InterruptedException
     */
    @Test
    public void testGlobalStatic() throws InterruptedException {
        Sync sync1 = new Sync();
        final Sync sync2 = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("starting...");
                sync2.read();
            }
        }).start();
        sync1.get(100);
    }

    /**
     * error example
     * @throws InterruptedException
     */
    @Test
    public void testWait() throws InterruptedException {
        new Object().wait(1);
        System.out.println("2");
    }

    @Test
    public void testWait1() throws InterruptedException {
        final Sync sync = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    sync.notify();
                }
            }
        }).start();
        sync.get(100000);
    }

    /**
     * wait方法必须有 synchronized关键字配合并且是同步对象是当前wait的调用者，否则IllegalMonitorStateException
     * @throws InterruptedException
     */
    @Test
    public void testWait3() throws InterruptedException {
        final Sync sync = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                sync.notifyLock();
                try {
                    sync.waitLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        sync.waitLock();
    }
    static private class Sync {
        private static class Lock{}
        private static Lock lock = new Lock();
//        private Lock lock = new Lock();


        public String get(long timeout) throws InterruptedException {
            synchronized (lock) {
                lock.wait();
                if(timeout != 0) {
                    System.out.println("timeout : " + timeout);
                }
                return "kevin";
            }
        }

        public void notifyLock(){
            synchronized (lock) {
                System.out.println("notifyLock");
                lock.notify();
            }
        }

        public void waitLock() throws InterruptedException {
            synchronized (lock) {
                System.out.println("locking");
                lock.wait();
            }
        }

        public void read() {
            synchronized (lock) {
                System.out.println("kevin is reading");
            }
        }
    }
    private class MyStringBuffer implements CharSequence {

        public int length() {
            return 0;
        }

        public char charAt(int index) {
            return 0;
        }

        public CharSequence subSequence(int start, int end) {
            return null;
        }
    }

    private class MySubStringBuffer extends MyStringBuffer {

    }
}
