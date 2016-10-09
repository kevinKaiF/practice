package y2016.m08.day20160823.app;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-23 PM02:14
 */
public class ConfigUpdater {
    public static final String PATH = "/config";

    private ActiveKeyValueStore activeKeyValueStore;
    private Random random = new Random();

    public ConfigUpdater(String host) throws IOException, InterruptedException {
        activeKeyValueStore = new ActiveKeyValueStore();
        activeKeyValueStore.connect(host);
    }

    public void run() throws KeeperException, InterruptedException {
        while (true) {
            String value = String.valueOf(random.nextInt(100));
            activeKeyValueStore.write(PATH, value);
            System.out.printf("Set %s to %s \n", PATH, value);
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) throws Exception{
        ConfigUpdater configUpdater = new ConfigUpdater(args[0]);
        configUpdater.run();
    }
}
