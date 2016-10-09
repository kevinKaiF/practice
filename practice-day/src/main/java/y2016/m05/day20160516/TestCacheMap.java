package y2016.m05.day20160516;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-16 PM01:43
 */
public class TestCacheMap {
    @Test
    public void testCacheMap() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);


        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(semaphore.availablePermits());
                System.out.println(semaphore.drainPermits());  // 清空所有允许的数目

                System.out.println(semaphore.availablePermits());
//                System.out.println(semaphore.drainPermits());
                System.out.println(semaphore.getQueueLength());   // queueLength 有线程才有意义，表示被阻塞的线程数目

                System.out.println(semaphore.availablePermits());
//                System.out.println(semaphore.drainPermits());
                System.out.println(semaphore.getQueueLength());
            }
        }).start();
        semaphore.acquire();
        Thread.currentThread().join();

    }
}
