package y2017.m01.d13;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/13
 */
public class TestThreadLocal {
    @Test
    public void testThreadLocal() {
        ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "22";
            }
        };
    }

    public static void main(String[] args) {
        final ThreadLocal<StringBuilder> threadLocal = new InheritableThreadLocal<StringBuilder>() {
            @Override
            protected StringBuilder initialValue() {
                return new StringBuilder("22");
            }
        };

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocal.get().append(Thread.currentThread().getName());
                    System.out.println(threadLocal.get());
                }
            }));
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        System.out.println("result : " + threadLocal.get());


    }
}
