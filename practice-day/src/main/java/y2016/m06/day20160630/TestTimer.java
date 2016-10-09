package y2016.m06.day20160630;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-30 AM09:57
 */
public class TestTimer {
    /**
     * Timer底层维护了一个TimerThread线程，TaskQueue将TimerTask添加到底层的数组中，
     * TimerThread会一直loop执行，TaskQueue中的任务
     * @throws InterruptedException
     */
    @Test
    public void testTimer() throws InterruptedException {
        Timer timer = new Timer("kevin");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                while (true) {
                    System.out.println(Thread.currentThread().getName() + ":" +System.currentTimeMillis());
//                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
//                while (true) {
                    System.out.println("2222");
//                }
            }
        };
        timer.schedule(timerTask1, 1500, 1000);

        Thread.sleep(20000);
//        timer.cancel();
        timerTask.cancel();

    }

    @Test
    public void testThreadInterrupt() {
        System.out.println(1);
        Thread.interrupted();
        Thread.currentThread().interrupt();
        System.out.println(2);
    }
}
