package y2016.m08.day20160823.app;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-23 PM02:14
 */
public class ConfigWatcher implements Watcher {

    private ActiveKeyValueStore activeKeyValueStore;

    public ConfigWatcher(String host) throws IOException, InterruptedException {
        activeKeyValueStore = new ActiveKeyValueStore();
        activeKeyValueStore.connect(host);
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        String value = activeKeyValueStore.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s \n", ConfigUpdater.PATH, value);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            try {
                displayConfig();
            } catch (KeeperException e) {
                e.printStackTrace();
                System.out.printf("KeepException : %s. Exiting \n", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ConfigWatcher watcher = new ConfigWatcher(args[0]);
        watcher.displayConfig();
    }
}
