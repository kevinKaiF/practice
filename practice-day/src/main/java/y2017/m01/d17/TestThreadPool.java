package y2017.m01.d17;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2017-01-17 PM04:47
 */
public class TestThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 2; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("test");
                }
            });
        }

//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (executorService.isShutdown()) {
//                System.out.println("shutdown");
//            }
//        }

        System.out.println("terminated");
    }
}
