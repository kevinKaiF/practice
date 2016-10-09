package y2016.m08.day20160823.zk;

import org.apache.zookeeper.*;

/**
 * 创建组
 *
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 AM11:42
 */
public class CreateGroup extends ConnectionWatcher {
    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        // @param path 节点路径
        // @param data 节点的内容
        // @param acl 节点的访问控制列表，这里是开放所有权限
        // @param mode 节点类型，ephemeral还是persistent
        String createPath = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("createPath : " + createPath);
    }

}
