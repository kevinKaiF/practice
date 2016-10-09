package y2016.m05.day20160509;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-09 AM10:55
 */
public class ThreadTest {
    @Test
    /**
     * 通常情况下，new Thread的方式的实现是init(null, target, "Thread-" + nextThreadNum(), 0)
     * 即init(ThreadGroup g, Runnable target, String name,long stackSize)，
     * Thread.UncaughtExceptionHandler对线程执行的异常进行处理，这个接口直接被ThreadGroup实现，
     * （jdk6以下，g为null，所以线程中出现的异常并未被处理）
     */
    public void threadTest1() {
        new Thread(new Runnable() {
            public void run() {
                throw new RuntimeException("testing RuntimeException");
            }
        }).start();
        System.out.println("ending");
    }

    @Test
    /**
     *
     */
    public void threadTest2() throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            public void run() {
                throw new RuntimeException("testing RuntimeException");
            }
        }, "threadTest2");
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                StringWriter writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer, true);
                e.printStackTrace(printWriter);
                System.out.print(writer.getBuffer().toString());
            }
        });
        t.start();

        t.join(); // 等我死
        System.out.println("ending");
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                System.out.println(e.getStackTrace().toString());
            }
        });
        for (int i=5;i >=0 ;i--) {
            int value = 5 / i;
            System.out.println(value);
        }
    }

    @Test
    /**
     * 无效？
     */
    public void threadTest3() {
        Thread.currentThread().setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                System.out.println(e.getStackTrace().toString());
            }
        });
        for (int i=5;i >=0 ;i--) {
            int value = 5 / i;
            System.out.println(value);
        }
    }

    @Test
    public void charsetTest() {
        System.out.println(Charset.defaultCharset().name());
    }

    interface A {
        void test();
    }

    class B implements A {
        public void test() throws IllegalArgumentException {
            System.out.println(11);
        }
    }
}
