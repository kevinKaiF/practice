package y2016.m09.d07;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-07 AM10:51
 */
public class DistributedLock {
    private static final int SESSION_TIMEOUT = 5000;
    private String DEFAULT_ZOOKEEPER_HOST = "10.0.0.166,10.0.0.169,10.0.0.170,10.0.0.165";
    private final String root = "/cookieLock";
    private String host ; // 使用默认的2181端口

    private String token;
    private String path;
    private int taskNum;   // 并行的数目
    private boolean validated;
    protected ZooKeeper zooKeeper;
    private Semaphore waitSignal = new Semaphore(0);
    private CountDownLatch connectedSignal = new CountDownLatch(1);


    private DistributedLock() {
        throw new UnsupportedOperationException();
    }

    public DistributedLock(int taskNum) {
        this.taskNum = taskNum;
        this.validated = false;
        this.host = DEFAULT_ZOOKEEPER_HOST;
    }
    public DistributedLock(int taskNum, String host) {
        this.taskNum = taskNum;
        this.validated = false;
        this.host = host;
    }


    public DistributedLock connect() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, SESSION_TIMEOUT, null);
        zooKeeper.register(new ConnectionWatcher(zooKeeper, root, connectedSignal));
        connectedSignal.await();
        return this;
    }


    public DistributedLock joinGroup(String token) {
        try {
            this.token = token;
            this.path = root + "/" + this.token;
            Stat stat = zooKeeper.exists(path, false);
            if (stat == null) {
                zooKeeper.create(path, String.valueOf(taskNum).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                zooKeeper.exists(path, new NodeDataChangedWatcher(waitSignal));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this;
    }

    public DistributedLock joinGroup(int token) {
        try {
            this.token = String.valueOf(token);
            this.path = root + "/" + this.token;
            Stat stat = zooKeeper.exists(path, true);
            if (stat == null) {
                zooKeeper.create(path, String.valueOf(taskNum).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                zooKeeper.exists(path, new NodeDataChangedWatcher(waitSignal));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean lock() {
        try {
            boolean wait = false;
            while (!wait) {
                List<String> children = zooKeeper.getChildren(root, false);
                if(!validated) {
                    if(children.size() != taskNum) {
                        Thread.sleep(1000);
                        continue;
                    } else {
                        validated = true;
                    }
                }

                // 遍历并查找最小节点
                for (String child : children) {
                    if (Integer.valueOf(child) < Integer.valueOf(token)) {
                        // 如果不是则继续等待
                        wait = true;
                        break;
                    }
                }

                if (wait) {
                    waitSignal.acquire();   // 等待被释放
                } else {
                    wait = true;
                }
            }

            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void unlock() throws InterruptedException, KeeperException {
        deleteNode();
        connectedSignal.await();
        waitSignal.release();
    }

    /**
     * 删除当前节点.
     *
     * @throws KeeperException      the keeper exception
     * @throws InterruptedException the interrupted exception
     * @author : kevin
     * @date : 2016-09-08 10:14:45
     */
    private void deleteNode() throws KeeperException, InterruptedException {
        if(path != null) {
            Stat stat = zooKeeper.exists(path, false);
            if(stat != null) {
                // 监听root子节点变化
                zooKeeper.getChildren(root, true);
                zooKeeper.delete(path, -1);
            }
        }
    }

    public void close() throws KeeperException, InterruptedException {
        zooKeeper.close();
    }

    private class ConnectionWatcher implements Watcher {
        private ZooKeeper zooKeeper;
        private String root;
        private CountDownLatch connectedSignal;

        private ConnectionWatcher() {
        }

        public ConnectionWatcher(ZooKeeper zooKeeper, String root, CountDownLatch connectedSignal) {
            this.zooKeeper = zooKeeper;
            this.root = root;
            this.connectedSignal = connectedSignal;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    Stat stat = zooKeeper.exists(root, false);
                    if (stat == null) {
                        try {
                            zooKeeper.create(root, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    connectedSignal.countDown();
                }

                System.out.println("================================================================ type : " + watchedEvent.getType() + ", zk : " + zooKeeper.toString());
                if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                    List<String> children = zooKeeper.getChildren(root, false);
                    System.out.println(children);
                    String miniChild = null;
                    if(children.size() == 1) {
                        miniChild = children.get(0);
                    } else {
                        for (String child : children) {
                            if(miniChild == null || Integer.valueOf(miniChild) > Integer.valueOf(child)) {
                                miniChild = child;
                            }
                        }
                    }

                    String path = root + "/" + miniChild;
                    System.out.println("miniChild :" + path);
                    Stat stat = zooKeeper.exists(path, true);
                    zooKeeper.setData(path, String.valueOf(children.size()).getBytes(), stat.getVersion());
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class NodeDataChangedWatcher implements Watcher {

        private Semaphore waitSignal;

        public NodeDataChangedWatcher(Semaphore waitSignal) {
            this.waitSignal = waitSignal;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                System.out.println(">>>>> " + watchedEvent.getType());
                if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                    System.out.println("NodeDataChanged, " + watchedEvent.getPath());
                    waitSignal.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
