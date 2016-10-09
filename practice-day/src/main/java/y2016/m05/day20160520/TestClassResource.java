package y2016.m05.day20160520;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-20 PM02:48
 */
public class TestClassResource {
    /**
     * /xxx 这种形式表示当前文件所在目录的同级目录
     * ./ 这种形式表示当前文件所在的目录
     */
    @Test
    public void testResource() throws IOException {
        URL url = TestClassResource.class.getResource("/");
        if(url != null) {
            String root = url.getFile();
            System.out.println(root);

            System.out.println(url.getPath());
            File file = new File(root);
            System.out.println(file.isDirectory());

            File file1 = new File(root + TestClassResource.class.getName().replaceAll("\\.", File.separator + File.separator) + ".class");
            System.out.println(file1.isFile());
            System.out.println(file1.getCanonicalPath()); // 获取标准的文件路径

            System.out.println(root.replaceFirst("/", "{}"));
        }
    }

    @Test
    public void testDot() {
        System.out.println("a.b.c".replaceAll("\\.", "|"));
        System.out.println(TestClassResource.class.getName().replaceAll("\\.", "\\\\"));
    }
}
