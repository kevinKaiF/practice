package y2016.m08.day20160823.zk;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 AM11:50
 */
public class ListGroup extends ConnectionWatcher {
    public void list(String groupName) {
        String path = "/" + groupName;
        try {
            List<String> children = zooKeeper.getChildren(path, false);
            if(children == null || children.isEmpty()) {
                System.out.printf("no members in group %s \n", groupName);
                System.exit(1);
            }

            for (String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ListGroup listGroup = new ListGroup();
        listGroup.connect(args[0]);
        listGroup.list(args[1]);
        listGroup.close();
    }
}
