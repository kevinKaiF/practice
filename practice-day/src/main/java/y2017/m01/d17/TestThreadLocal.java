package y2017.m01.d17;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2017-01-17 AM10:25
 */
public class TestThreadLocal {
    @Test
    public void testThreadLocal() {
//        final ThreadLocal<StringBuffer> bufferThreadLocal = new ThreadLocal<StringBuffer>() {
//            @Override
//            protected StringBuffer initialValue() {
//                return new StringBuffer("test");
//            }
//        };

        final CountDownLatch latch = new CountDownLatch(30);
        final ThreadLocal<StringBuffer> bufferThreadLocal = new InheritableThreadLocal<>();
        bufferThreadLocal.set(new StringBuffer("test"));
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            threadList.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(finalI + " >>>> " + bufferThreadLocal.get()); // null
                    bufferThreadLocal.set(new StringBuffer(Thread.currentThread().getName()));
                    System.out.println(finalI + " <<<< " + bufferThreadLocal.get());
                    latch.countDown();
                }
            }));
        }

        bufferThreadLocal.set(new StringBuffer("aaaa"));

        for (Thread thread : threadList) {
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(bufferThreadLocal.get());
    }

    @Test
    public void testMap() {

        final CountDownLatch latch = new CountDownLatch(30);
        final Map<String, StringBuffer> bufferThreadLocal = new HashMap<>();
        bufferThreadLocal.put("test", new StringBuffer(Thread.currentThread().getName()));
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            threadList.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(finalI + " >>>> " + bufferThreadLocal.get("test")); // null
                    bufferThreadLocal.put("test", new StringBuffer(Thread.currentThread().getName()));
                    System.out.println(finalI + " <<<< " + bufferThreadLocal.get("test"));
                    latch.countDown();
                }
            }));
        }

        bufferThreadLocal.put("test", new StringBuffer("aaaa"));

        for (Thread thread : threadList) {
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(bufferThreadLocal.get("test"));
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new ThreadLocalWorker());
        }

        executorService.shutdown();
    }
}

class ThreadLocalWorker implements Runnable {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "Initial Value";
        }
    };


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "'s ThreadLocal object's old value is " + threadLocal.get());
        threadLocal.set("New value is " + Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + " set new value to ThreadLocal object: "
                + threadLocal.get());

        try {
            //休眠1秒，看看别的线程里面的值修改是否影响了当前Thread
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " sleep for a while and the  value of ThreadLocal object is : "
                + threadLocal.get());
    }


}
