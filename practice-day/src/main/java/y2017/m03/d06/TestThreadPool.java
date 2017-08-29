package y2017.m03.d06;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/6
 */
public class TestThreadPool {
    private Logger logger = LoggerFactory.getLogger(TestThreadPool.class);

    @Test
    public void testThreadPool() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("kevin" + System.currentTimeMillis());
                return thread;
            }
        });

        Future<?> submit = poolExecutor.submit(new RunnableFuture<String>() {
            private volatile String name = Thread.currentThread().getName();

            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return name;
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        });

        poolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName());
            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Object o = submit.get();
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        poolExecutor.shutdown();
    }

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    @Test
    public void test2() {
        System.out.println(Integer.toBinaryString(RUNNING)); // 线程池最初的RUNNING状态为11100000000000000000000000000000
        System.out.println(ctl.get());                       // -536870912
        System.out.println(Integer.toBinaryString(CAPACITY));   // 容量  00011111111111111111111111111111
        System.out.println(Integer.toBinaryString(~CAPACITY));  // 取反，11100000000000000000000000000000

        /**
         * RUNNING肯定是负数，小于核心线程数目，每次addWork，其RUNNING状态加1
         * RUNNING是     11100000000000000000000000000000
         * SHUTDOWN是    00000000000000000000000000000000    0 << Integer.SIZE - 1
         * STOP是        00100000000000000000000000000000    1 << Integer.SIZE - 1
         * TIDYING是     01000000000000000000000000000000    2 << Integer.SIZE - 1
         * TERMINATED是  01100000000000000000000000000000    3 << Integer.SIZE - 1
         * 所以runStateOf采用&的方式很好的判断RUNNING,SHUTDOWN,STOP,TIDYING,TERMINATED的五种状态
         */
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));   // 0
        System.out.println(Integer.toBinaryString(STOP));       // 00100000000000000000000000000000
        System.out.println(Integer.toBinaryString(TIDYING));    // 01000000000000000000000000000000
        System.out.println(Integer.toBinaryString(TERMINATED)); // 01100000000000000000000000000000

        System.out.println(SHUTDOWN);
        System.out.println(STOP);

        System.out.println(-536870911);
        System.out.println(workerCountOf(-536870910));
    }


    @Test
    public void test3() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2));
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("3");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Thread.sleep(2000);
            threadPoolExecutor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadPoolExecutor);
    }
}
