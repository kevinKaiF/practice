package y2016.m08.day20160823.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 AM11:41
 */
public class ConnectionWatcher implements Watcher {
    private static final int SESSION_TIMEOUT = 5000;
    private String lock = "/lock";

    protected ZooKeeper zooKeeper;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    /**
     * zooKeeper实例化是个异步的过程.
     *
     * @param host the host
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @author : kevin
     * @date : 2016-08-23 11:16:13
     */
    public void connect(String host) throws IOException, InterruptedException {
        // zooKeeper实例化时，会启动一个线程链接到服务器，连接成功后会调用process回掉方法
        zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, this);
        // 等待连接建立
        connectedSignal.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            try {
                Stat stat = zooKeeper.exists(lock, false);
                if(stat == null) {
                    zooKeeper.create(lock, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectedSignal.countDown();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}
