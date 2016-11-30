package y2016.m11.d24;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/24
 */
public class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
        int myRow;

        Worker(int row) {
            myRow = row;
        }

        public void run() {
            processRow(myRow);

            try {
                barrier.await();
                System.out.println(System.currentTimeMillis());
            } catch (InterruptedException ex) {
                return;
            } catch (BrokenBarrierException ex) {
                return;
            }

//            System.out.println(System.currentTimeMillis());
        }

        private void processRow(int myRow) {
            System.out.println("myRow : " + myRow);
        }
    }

    public Solver(float[][] matrix) {
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N,
                new Runnable() {
                    public void run() {
                        System.out.println("lalalal");
                    }
                });
        for (int i = 0; i < N; ++i)
            new Thread(new Worker(i)).start();

        waitUntilDone();
    }

    private void waitUntilDone() {
        System.out.println("ending");
    }

    public static void main(String[] args) {
        Solver solver = new Solver(new float[100][]);
    }
}
