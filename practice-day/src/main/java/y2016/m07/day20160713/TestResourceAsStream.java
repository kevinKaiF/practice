package y2016.m07.day20160713;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-13 PM05:27
 */
public class TestResourceAsStream {
    @Test
    public void testResource() {
        InputStream inputStream = this.getClass().getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ""和.表示当前类所在包路径
        System.out.println(this.getClass().getResource(""));
        System.out.println(this.getClass().getResource("."));
        System.out.println(this.getClass().getResource("./"));
        // / 表示该类所在包的最外层目录
        System.out.println(this.getClass().getResource("/"));
        // /xx.xx 表示该类所在包的最外层目录下的文件xx.xx
        System.out.println(this.getClass().getResource("/test.properties"));
    }
}
