package y2016.m08.day20160818;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-18 PM02:35
 */
public class ThreadFixedPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
