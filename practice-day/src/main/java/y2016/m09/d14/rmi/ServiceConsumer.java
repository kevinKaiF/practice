package y2016.m09.d14.rmi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 PM03:04
 */
public class ServiceConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(ServiceConsumer.class);

    private CountDownLatch waitConn = new CountDownLatch(1);
    private volatile List<String> urlList = new ArrayList<>();

    public ServiceConsumer() {
        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            Stat stat = zk.exists(Constants.ZK_RMI, false);
            if (stat == null) {
                zk.create(Constants.ZK_RMI, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            List<String> children = zk.getChildren(Constants.ZK_RMI, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                        System.out.println("=========== NodeChildrenChanged");
                        watchNode(zk); // 当子节点发生变化，再次触发该事件，刷新客户端url列表，并重新绑定watcher
                        System.out.println(urlList);
                    }
                }
            });

            List<String> urls = new ArrayList<>();
            for (String child : children) {
                byte[] data = zk.getData(Constants.ZK_RMI + "/" + child, false, null);
                urls.add(new String(data));
            }

            urlList = urls;
            System.out.println("=== init " + urlList);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lookup t.
     *
     * @param <T>         the type parameter
     * @param serviceName the cn.bella.service name, 服务名称
     * @return the t
     * @author : kevin
     * @date : 2016-09-14 16:51:13
     */
    public <T extends Remote> T lookup(String serviceName) {
        T service = null;
        int size = urlList.size();
        if (size > 0) {
            for (String url : urlList) {
                // TODO 这里判断做的不够好
                if (url.substring(url.lastIndexOf("/") + 1).equals(serviceName)) {
                    return lookupService(url);
                }
            }
        }
        return service;
    }

    private <T extends Remote> T lookupService(String url) {
        T remote = null;
        try {
            remote = (T) Naming.lookup(url);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            if (e instanceof ConnectException) {
                LOGGER.error("connect exception, caused : ", e);
            } else {
                e.printStackTrace();
            }
        }
        return remote;
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
