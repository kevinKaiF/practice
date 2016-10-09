package y2016.m09.d12;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-07 PM04:34
 */
public class TestDistributedLock {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int len = 30;
        for (int i = 0; i < len; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DistributedLock0 lock = null;
                    try {
                        lock = new DistributedLock0(len).connect().joinGroup(String.valueOf(finalI));
                        lock.lock();
                        System.out.println(Thread.currentThread().getName());
                        lock.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
//                        try {
//                            lock.close();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (KeeperException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }).start();
        }
    }
}
