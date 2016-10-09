package y2016.m04.day20160412;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-12 AM11:25
 */
public class MyThread extends Thread {
    private final BlockingQueue<String> queue;

    private MyThread() {
        this(null);
    }

    public MyThread(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            System.out.println("线程被取消");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread(new LinkedBlockingDeque<String>());
        thread.start();
        thread.interrupt();
        Thread.sleep(2000);
        System.out.println(thread.isInterrupted());
    }
}
