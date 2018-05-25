package y2017.m09.d03;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/4
 */
public class TestCountDownLatch {
    @Test
    public void testCountDownLatch() {
        // 设置state的初始值，一旦state==0，
        // 共享模式
        // 非公平的
        // countDownLatch的行为是主线程是被动的，当全部子线程执行任务，才唤醒主线程
        // countDownLatch是无法重新置位，复用的
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // countDown进行tryReleaseShared,state-1并原子性更新state
                // 如果state为0，直接返回-1，加入等待队列
                // 如果nextState为0，则需要doReleaseShared，即唤醒其他等待的节点
                // 这种效果就是多线程共享的时候，多线程会主动唤醒等待节点，即CountDownLatch的await

                // countDown是释放锁的过程
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }
        }).start();


        try {
            // acquireSharedInterruptibly是获取共享锁的过程，可中断
            // tryAcquireShared则会判断state是否为0，如果为0，则返回1,无需阻塞。如果为-1，则doAcquireSharedInterruptibly
            // 添加共享模式节点，并两次尝试tryAcquireShared，如果失败，则LockSupport会park当前线程，直到多线程countDown到state为0
            countDownLatch.await();
            System.out.println("GG");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
