package y2017.m11.d29;

import org.junit.Before;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/29
 */
public class TestConcurrent {
    private ExecutorService executorService;

    @Before
    public void testBefore() {
        executorService = Executors.newFixedThreadPool(4);
    }

    @Test
    public void testConcurrent() throws InterruptedException {
        for (int j = 0; j < 5; j++) {
            SyncTimeUtil.setCurrentDate();
            final Timestamp currentDate = SyncTimeUtil.getCurrentDate();
            System.out.println("current : " + currentDate);
            for (int i = 0; i < 4; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("thread :" + currentDate);
                    }
                });
            }

            Thread.sleep(2000);
        }


    }

    @Test
    public void testSynchronizedQueue() throws InterruptedException {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        queue.put("test");
        System.out.println(queue);
        // offer的时候不会阻塞等待
        System.out.println(queue.offer("test2"));
        // SynchronousQueue不能进行迭代
        for (String s : queue) {
            System.out.println(s);
        }
        System.out.println(queue);
    }


    @Test
    /**
     * ThreadMXBean可以获取thread的信息
     */
    public void testThreadInfo() {
        System.out.println(dumpThreadInfo());
    }

    private String dumpThreadInfo() {
        final StringBuilder sb = new StringBuilder();
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Thread t = Thread.currentThread();
        ThreadInfo threadInfo = threadMXBean.getThreadInfo(t.getId());
        sb.append("{");
        sb.append("name=").append(t.getName()).append(",");
        sb.append("id=").append(t.getId()).append(",");
        sb.append("state=").append(threadInfo.getThreadState()).append(",");
        sb.append("lockInfo=").append(threadInfo.getLockInfo());
        sb.append("}");
        return sb.toString();
    }
}
