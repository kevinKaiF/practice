package y2016.m08.day20160825;

import y2016.m08.day20160811.ParseDir;
import y2016.m08.day20160811.ParseLog;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-25 PM02:20
 */
public class ParseOasBrowserSecondLog {
    public static void main(String[] args) {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\hive";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\hive\\oas_browser_second_log";
        final String type = "ae3bae0cbe534dc7a3ed9e332606de18"; // 悦采
        String separator = "\u0001";

        ParseLog parseLog = new ParseLog(separator);
        parseLog.clearRoot(root);
        parseLog.multiParse(root, type, new ParseDir() {
            @Override
            public List<File> parseSubFile() {
                List<File> fileList = new ArrayList<>();
                for (String dir : new String[]{dir1}) {
                    File browserFile = new File(dir);
                    fileList.addAll(Arrays.asList(browserFile.listFiles()));
                }
                return fileList;
            }

            @Override
            public Map<String, List<String>> toMap(List<File> subFiles) {
                Map<String, List<String>> map = new LinkedHashMap<>();
                String name = "oas_browser_second_log.log";
                for (final File file : subFiles) {
                    if (file.isFile()) {
                        List<String> subNames = map.get(name);
                        if (subNames == null) {
                            map.put(name, new ArrayList<String>() {
                                {
                                    add(file.getPath());
                                }
                            });
                        } else {
                            subNames.add(file.getPath());
                        }
                    }
                }
                return map;
            }
        });
    }

    @Test
    public void testParseFlow() {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\hive";
        String hiveLog = "C:\\Users\\Administrator\\Desktop\\etl\\hiveLog.txt";
        String separator = "\u0001";
        ParseLog parseLog = new ParseLog(separator);
        parseLog.parseFlow(root, hiveLog, null);
    }

    @Test
    public void testSplit() {
        String s = "1472006755113\u0001\u0001http://zhenghai.app.yuecai.com/eip/jun/index.htm\u0001\u0001Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\u0001ae3bae0cbe534dc7a3ed9e332606de18\u0001 112.25.90.48\u0001 112.25.90.102_1472000931956\u0001烟台正海电子采购管理平台\u0001none\u0001none\u0001直接访问\u0001江苏\u000130000\u0001Windows XP\u0001PC端\u0001其他\u0001none\u0001谷歌浏览器\u00012016\u000108\u000124\u000110";
        System.out.println(Arrays.toString(s.split("\u0001")));
    }

    @Test
    public void testIndexOf() {
        String s = "http://zhenghai.app.yuecai.com/eip/jun/index.htm,";
        System.out.println(s.indexOf("/"));
    }
}
