package y2016.m11.d24;

import java.util.concurrent.CountDownLatch;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/24
 */
public class Driver {
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.start(10);
    }
    public void start(int n) {
        try {
            CountDownLatch startSignal = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(n);
            for (int i = 0; i < n; i++) {
                new Thread(new Worker(startSignal, doneSignal)).start();
            }

            startSignal.countDown();
            doSomething();
            doneSignal.await();
            System.out.println("ending");
            // 最终countDownLatch.getCount数目为0
            System.out.println(doneSignal.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void doSomething() {

    }

    class Worker implements Runnable {
        CountDownLatch startSignal;
        CountDownLatch doneSignal;

        private Worker() {
        }

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                doWork();
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            System.out.println("Thread : [ " + Thread.currentThread().getName() + " ], count : " + doneSignal.getCount());
        }
    }
}
