package y2016.m05.day20160519;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-19 PM05:08
 */
public class Sync {
    private static class Lock{}
//    private static Lock lock = new Lock();  // 全局锁
    private Lock lock = new Lock();

    public void read() throws InterruptedException {
        synchronized (lock) {
//            System.out.println("read");
//            Thread.sleep(10000);
            lock.wait();
//            Semaphore semaphore = new Semaphore(0);
//            semaphore.acquire();
            System.out.println("has read");
        }
    }

    public void write() {
        synchronized (lock) {
            System.out.println("write");
        }
    }

    public static void main(String[] args) throws InterruptedException {


    }

    @Test
    public void test1() throws InterruptedException {
        final Sync sync = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("reading");
                    sync.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Thread.sleep(1000);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sync.write();
            }
        }).start();new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sync.write();
            }
        }).start();new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sync.write();
            }
        }).start();new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sync.write();
            }
        }).start();


        Thread.currentThread().join();
    }

    @Test
    public void test2() throws InterruptedException {
        final Sync sync = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    sync.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final Sync sync1 = new Sync();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sync1.write();
            }
        }).start();

        Thread.currentThread().join();
    }
}
