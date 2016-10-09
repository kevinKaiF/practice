package y2016.m08.day20160823.app;

import y2016.m08.day20160823.zk.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 PM02:57
 */
public class ActiveKeyValueStore extends ConnectionWatcher {
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void write(String path, String value) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        if(stat == null) {
            zooKeeper.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zooKeeper.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
        byte[] bytes = zooKeeper.getData(path, watcher, null);
        return new String(bytes, CHARSET);
    }
}
