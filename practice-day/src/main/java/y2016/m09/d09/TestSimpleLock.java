package y2016.m09.d09;

import org.apache.zookeeper.KeeperException;
import y2016.m09.d07.DistributedLock;

import java.io.IOException;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-09 AM10:33
 */
public class TestSimpleLock {
    public static void main(String[] args) {
        final int len = 30;
        for (int i = 0; i < len; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleLock lock = null;
                    try {
                        lock = new SimpleLock().connect().joinGroup(finalI);
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
                    }
                }
            }).start();
        }
    }
}
