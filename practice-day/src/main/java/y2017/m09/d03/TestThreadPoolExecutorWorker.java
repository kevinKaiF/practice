package y2017.m09.d03;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/9/4
 */
public class TestThreadPoolExecutorWorker {
    /**
     * 11100000000000000000000000000000 这种设计，高位3位作为状态，低位29位作为统计Work数目
     * 111状态表示RUNNING,
     * 000状态表示SHUTDOWN,
     * 001状态表示STOP，
     * 010状态表示TIDYING，
     * 011状态表示TERMINATED
     */
    @Test
    public void testOr() {
        System.out.println(1 | 0);
        System.out.println(-1 | 1);
        System.out.println(-4 & -7);
        System.out.println(4 & 7);
        System.out.println(-30 & 7);
        final int COUNT_BITS = Integer.SIZE - 3;   // 29
        final int CAPACITY = (1 << COUNT_BITS) - 1;

        // runState is stored in the high-order bits
        final int RUNNING = -1 << COUNT_BITS;
        System.out.println(format("RUNNING +1:") + format(RUNNING +1));
        final int SHUTDOWN = 0 << COUNT_BITS;
        final int STOP = 1 << COUNT_BITS;
        final int TIDYING = 2 << COUNT_BITS;
        final int TERMINATED = 3 << COUNT_BITS;
        final int INITIAL = RUNNING | 0;
//        COUNT_BITS:11101
//         CAPACITY:   11111111111111111111111111111
//        ~CAPACITY:11100000000000000000000000000000
//          RUNNING:11100000000000000000000000000000
//         SHUTDOWN:                               0
//             STOP:  100000000000000000000000000000
//          TIDYING: 1000000000000000000000000000000
//       TERMINATED: 1100000000000000000000000000000
//          INITIAL:11100000000000000000000000000000
        System.out.println(format("COUNT_BITS:") + Integer.toBinaryString(COUNT_BITS));
        System.out.println(format("CAPACITY:") + format(CAPACITY));
        System.out.println(format("~CAPACITY:") + format(~CAPACITY));
        System.out.println(format("RUNNING:") + format(RUNNING));
        System.out.println(format("SHUTDOWN:") + format(SHUTDOWN));
        System.out.println(format("STOP:") + format(STOP));
        System.out.println(format("TIDYING:") + format(TIDYING));
        System.out.println(format("TERMINATED:") + format(TERMINATED));
        System.out.println(format("INITIAL:") + format(INITIAL));
    }

    private String format(int CAPACITY) {
        return String.format("%32s", Integer.toBinaryString(CAPACITY));
    }

    private String format(String CAPACITY) {
        return String.format("%14s", CAPACITY);
    }

    @Test
    public void testThreadPool() {
        int corePoolSize = 1;
        int maximumPoolSize = 2;
        int keepTimeLive = 2000;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);
        ThreadFactory threadFactory = new LocalThreadFactory();
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                                                                       maximumPoolSize,
                                                                       keepTimeLive,
                                                                       TimeUnit.SECONDS,
                                                                       workQueue,
                                                                       threadFactory,
                                                                       handler);
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        threadPoolExecutor.shutdown();
        threadPoolExecutor.shutdownNow();

        while (!threadPoolExecutor.isTerminated()) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor.shutdownNow();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("GG");
    }

    class LocalThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            return thread;
        }
    }

    @Test
    /**
     *
     * clt设计很有意思，32位bit中，最高三位表示runstate,其余的29位表示workercount,初始值是11100000000000000000000000000000
     * 表示RUNNING状态，运行的worker为0.
     *
     * Worker 继承了 AbstractQueuedSynchronizer，并重写了tryAcquire tryRelease是独占锁的实现，并且是互斥的，不可重入
     * 另外，Worker实现了Runnable接口，实现了run方法的逻辑
     * 其属性thread是getThreadFactory().newThread(this)实现的，
     * 其属性firstTask是execute执行的Runnable对象
     */
    public void testThreadPool1() throws InterruptedException {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                while (true ) {
//                    System.out.println(Thread.currentThread().getName());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                while (true ) {
//                    System.out.println(Thread.currentThread().getName());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        Thread.currentThread().join();
    }
}
