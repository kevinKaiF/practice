package y2016.m09.d14.rmi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-14 PM03:40
 */
public class ServiceProvider {
//    private static Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);
    private CountDownLatch waitConn = new CountDownLatch(1);

    private ZooKeeper zooKeeper;

    public ServiceProvider() {
        this.zooKeeper = connectServer();
    }

    public void publish(Remote remote, String host, String port) {
        String url = publishService(remote, host, port);
        if (url != null) {
            create(zooKeeper, url, remote.getClass().getName());
        }
    }

    public void unPublish(Remote remote) {
        try {
            String path = Constants.ZK_RMI + "/" + remote.getClass().getName();
            Stat stat = zooKeeper.exists(path, false);
            if (stat != null) {
                zooKeeper.delete(path, -1);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String publishService(Remote remote, String host, String port) {
        String url = null;
        try {
            url = MessageFormat.format("rmi://{0}:{1}/{2}", host, port, remote.getClass().getName());
            LocateRegistry.createRegistry(Integer.parseInt(port));
            Naming.rebind(url, remote);
//            LOGGER.debug("publish rmi service url : ({})", url);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private void create(ZooKeeper zooKeeper, String url, String serviceName) {
        try {
            Stat stat = zooKeeper.exists(Constants.ZK_RMI, false);
            if (stat == null) {
                zooKeeper.create(Constants.ZK_RMI, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            String path = Constants.ZK_RMI + "/" + serviceName;
            if (zooKeeper.exists(path, false) == null) {
                // 在指定的节点下创建服务名称
                zooKeeper.create(path, url.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
//            LOGGER.debug("create zk node ({} -> {})", path, url);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper connectServer() {
        try {
            ZooKeeper zk = new ZooKeeper(Constants.ZK_HOST, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        waitConn.countDown();
                    }
                }
            });

            waitConn.await();
            return zk;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
