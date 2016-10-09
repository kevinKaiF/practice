package y2016.m08.day20160803;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-03 PM04:06
 */
public class BooleanLatch {
    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int arg) {
            return isSignalled() ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }

        private boolean isSignalled() {
            return getState() != 0;
        }
    }

    private final Sync sync = new Sync();

    public boolean isSignalled() {
        return sync.isSignalled();
    }

    public void signal() {
        sync.releaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Test
    public void testBooleanLatch() throws InterruptedException {
        final BooleanLatch booleanLatch = new BooleanLatch();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                booleanLatch.signal();
                System.out.println("thread1");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                booleanLatch.signal();
                System.out.println("thread2");
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                booleanLatch.signal();
                System.out.println("thread3");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            booleanLatch.await();
            System.out.println("ending");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread.currentThread().join(1000);
    }

    @Test
    public void testCountDownLatch() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("t1");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("t2");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("t3");
            }
        });

        t1.start();
        t2.start();
        t3.start();

        countDownLatch.await();
        System.out.println("ending");

    }
}
