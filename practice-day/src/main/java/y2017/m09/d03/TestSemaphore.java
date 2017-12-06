package y2017.m09.d03;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/4
 */
public class TestSemaphore {
    @Test
    public void testSemaphore() {
        // 设置state的初始值
        // 共享模式
        // 公平和非公平
        // Semaphore是可以重新置位，可以复用的
        Semaphore semaphore = new Semaphore(1);
        try {
            // acquire会进行acquireSharedInterruptibly，
            // 1.中断的获取，如果acquire的过程中阻塞了，可以被中断，
            // 使得锁的获取是可控的，灵活的，不是无限等待的
            // 2.使用的共享模式，而非独占模式，这样使得Semaphore可以在多线程中也可以acquire

            // acquire的过程中permits减去当前需要acquire的数目，并原子性的更新state
            // tryAcquireShared的返回值为remaining，如果小于0说明permits已经用完了，则加入等待队列

            // 默认的队列是非公平队列，会直接进行。
            // 如果是公平队列，tryAcquireShared则hasQueuedPredecessors判断队列中是否有等待，如果有等待则直接返回-1，直接加入队列
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // release则会进行tryReleaseShared，会将state+1，并原子性更新state。
        // doReleaseShared会通知队列中等待的节点，唤醒等待节点的acquire请求，并再次进行tryAcquire
        semaphore.release();
    }
}
