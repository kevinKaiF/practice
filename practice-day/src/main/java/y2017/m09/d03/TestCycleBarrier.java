package y2017.m09.d03;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/4
 */
public class TestCycleBarrier {
    @Test
    public void testCycleBarrier() {
        // CyclicBarrier定义了parties的数目，表示可控制子线程的数目
        // CyclicBarrier相对于CountDownLatch，则是主线程更加有主动权，而且可以循环利用
        // CyclicBarrier的阻塞和唤醒都是靠Condition#await方法实现的，当parties==0则进行唤醒操作
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("GG");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().join();
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
