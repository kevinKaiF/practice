package y2016.m06.day20160629;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-29 AM11:57
 */
public class TestThreadPool {
    @Test
    public void testThreadPool() throws InterruptedException {
        final AtomicBoolean isStop = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isStop.get()) {
                        break;
                    }
                }
            }
        });


        Thread.sleep(2000);
        executorService.shutdownNow();
        isStop.getAndSet(true);
        while (!executorService.isTerminated()) {
            System.out.println("111");
        }

        System.out.println("ending");

    }

    @Test
    public void testThreadPool1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("stop sleep");
                        break;
                    }
                }
            }
        });


        Thread.sleep(2000);
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
            System.out.println("111");
        }

        System.out.println("ending");

    }

    @Test
    public void testThrow() {
        try {
            int a = 1;
            do {
                Object o = null;
                o.toString();
            } while (a > 0);
            int s = 2;
            System.out.println(111);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAutoBox() {
        Integer a = 1;
        Integer b = a;
        b++;
        System.out.println(a);
    }
}
