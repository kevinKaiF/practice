package y2016.m07.day20160715;

import cn.bidlink.framework.jk.JkDownload;
import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-15 PM03:36
 */
public class TestJkClient {
    @Test
    public void testJkClient() throws Exception {
        System.setProperty("jk.username", "huyu");
        System.setProperty("jk.userpwd", "nyc");
        System.setProperty("jk.dfaddress", "http://10.0.2.24:8080/job/dc-canal/18/cn.bidlink.canal$canal-server/artifact/cn.bidlink.canal/canal-server/0.0.1-SNAPSHOT/canal-server-0.0.1-SNAPSHOT-assembly.tar.gz");
        System.setProperty("jk.dflocal.dir", "E:\\logs");
        JkDownload.main(new String[]{});
    }
}
