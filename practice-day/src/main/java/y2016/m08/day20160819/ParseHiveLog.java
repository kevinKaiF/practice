package y2016.m08.day20160819;

import y2016.m08.day20160811.ParseDir;
import y2016.m08.day20160811.ParseLog;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-19 AM10:43
 */
public class ParseHiveLog {
    private static Logger LOGGER = Logger.getLogger(ParseHiveLog.class);
    public static void main(String[] args) {
        LOGGER.info("running");
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\dw\\parse";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\dw\\browser";
        final String type = "ae3bae0cbe534dc7a3ed9e332606de18";

        ParseLog parseLog = new ParseLog();
        parseLog.clearRoot(root);
        parseLog.multiParse(root, type, new ParseDir() {
            @Override
            public List<File> parseSubFile() {
                List<File> fileList = new ArrayList<>();
                for (String dir : new String[]{dir1}) {
                    File browserFile = new File(dir);
                    for(File file : browserFile.listFiles()) { // 月
                        for (File file1 : file.listFiles()) { // 天
                            for (File file2 : file1.listFiles()) {
                                fileList.addAll(Arrays.asList(file2.listFiles()));
                            }
                        }
                    }
                }
                return fileList;
            }

            @Override
            public Map<String, List<String>> toMap(List<File> subFiles) {
                Map<String, List<String>> map = new LinkedHashMap<>();
                for (final File file : subFiles) {
                    if (file.isFile() && file.getName().contains("part")) {
                        final String filePath = file.getPath();
                        String totalName = "browser.log-2016-" + filePath.substring(filePath.indexOf("dw") + 3, filePath.indexOf("part") - 4).replaceAll("\\\\", "-") + ".log";
                        List<String> subNames = map.get(totalName);
                        if (subNames == null) {
                            map.put(totalName, new ArrayList<String>() {
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
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\dw\\parse";
        String parseIpLog = "C:\\Users\\Administrator\\Desktop\\etl\\dw\\ipLog.txt";
        String ignoredIp = "124.207.8.210,211.151.182.214,219.143.69.27,113.57.165.74";
        ParseLog parseLog = new ParseLog();
        parseLog.parseFlow(root, parseIpLog, ignoredIp);
    }

    @Test
    public void testError() {
        System.err.println("222");
    }

}
