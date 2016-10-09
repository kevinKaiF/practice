package y2016.m05.day20160517;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-17 AM10:56
 */
public class TestThreadPool {
    /**
     * ThreadPoolExecutor在execute过程中出现异常的话有4种处理异常的策略，其实现了RejectedExecutionHandler接口
     * 1.ThreadPoolExecutor.AbortPolicy 直接抛出RejectedExecutionException（RunTimeException）异常，终止任务执行 (默认)
     * 2.ThreadPoolExecutor.DiscardPolicy 忽略，什么也不用做
     * 3.ThreadPoolExecutor.DiscardOldestPolicy 如果线程池未关闭，忽略最老的，也就是忽略队头，再次执行当前任务。
     * 4.ThreadPoolExecutor.CallerRunsPolicy 如果线程池为关闭，再次执行当前任务。
     * @throws InterruptedException
     */
    @Test
    public void testThreadPoolExecutorPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "11111");
                }
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "22222");
                }
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "33333");
                }
            }
        });
        Thread.currentThread().join();
    }


    /**
     * ThreadPoolExecutor中
     * corePoolSize 线程池中活跃的线程数目，即便是空闲的
     * maximumPoolSize 线程池中最大的线程数目
     * keepAliveTime 当线程的数目大于核心线程数目时，空闲线程等待任务的最长时间
     * unit 等待时间的单位
     * workQueue execute方法所submit的任务队列
     * threadFactory 线程工厂方法
     * handler execute异常的处理策略
     * @throws InterruptedException
     */
    @Test
    public void testThreadPoolExecutorConstructor() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        Thread.currentThread().join();
    }

    /**
     * [NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED]
     */
    @Test
    public void testThreadState() {
        System.out.println(Arrays.toString(Thread.State.values()));
    }

    @Test
    public void testExecutors() throws InterruptedException {
        // 底层队列使用SynchronousQueue
//        Executors.newCachedThreadPool();

        final SynchronousQueue<String> queue = new SynchronousQueue<String>();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ":" + queue.offer(Math.random() * 100 + ""));
                }
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + ":" + queue.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.currentThread().join();

    }

    /**
     * Exchanger成对交换线程内的数据
     * @throws InterruptedException
     */
    @Test
    public void testExchanger() throws InterruptedException {
        final Exchanger<List<String>> exchanger = new Exchanger<List<String>>();

        new Thread(new Runnable() {
            public void run() {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    list.add(String.valueOf(i));
                }
                try {
                    List<String> list1 = exchanger.exchange(list);
                    System.out.println("1:" + list1.toString());

                    Thread.sleep(1000);
                    List<String> list2 = exchanger.exchange(list);
                    System.out.println("11:" + list1.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
//                    Thread.sleep(1000);
                    List<String> list = exchanger.exchange(Arrays.asList(new String[] {"A", "B", "C"}));
                    System.out.println("2:" + list.toString());

                    List<String> list1 = exchanger.exchange(Arrays.asList(new String[] {"A", "B", "C"}));
                    System.out.println("22:" + list1.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.currentThread().join();
    }
}
