package y2016.m07.day20160713;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-13 PM01:56
 */
public class SystemTimer {
    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static volatile long time = System.currentTimeMillis();

    private static class TimerTask implements Runnable {
        public void run() {
            time = System.currentTimeMillis();
        }
    }

    static {
        executorService.scheduleAtFixedRate(new TimerTask(), 1000, 1000, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executorService.shutdownNow();
            }
        });
    }


}
