package y2016.m08.day20160823.zk;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 AM11:51
 */
public class DeleteGroup extends ConnectionWatcher {
    public void delete(String groupName) {
        String path = "/" + groupName;
        // 先删除子节点，再删除该节点
        try {
            List<String> children = zooKeeper.getChildren(path, false);
            for (String child : children) {
                zooKeeper.delete(path + "/" + child, -1); // -1会忽略版本号检测，直接删除
            }

            zooKeeper.delete(path, -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
