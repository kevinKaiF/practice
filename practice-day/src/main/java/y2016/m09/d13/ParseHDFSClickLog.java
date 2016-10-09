package y2016.m09.d13;

import org.junit.Test;
import y2016.m08.day20160811.ParseDir;

import java.io.File;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-13 AM11:00
 */
public class ParseHDFSClickLog {
    public static void main(String[] args) {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\click\\parse";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\click\\browser";
//        final String type = "b1d760e5806e41e9ad509c8acab30bc5";  // 日志系统
//        final String type = "ae3bae0cbe534dc7a3ed9e332606de18"; // 悦采
//        final String type = "9b2978902fa64498a2d3330d1f49962d"; // 比联网-广告
        final String type = "35b2b795d689428f848d1edf059a9b7a"; // 比联网2.0
//        final String type = "0a6ce57d790d4e3bb00456516cf406d1"; // 老必联

        ParseClickLog parseClickLog = new ParseClickLog();
        parseClickLog.clearRoot(root);
        parseClickLog.multiParse(root, type, new ParseDir() {
            @Override
            public List<File> parseSubFile() {
                List<File> fileList = new ArrayList<>();
                for (String dir : new String[]{dir1}) {
                    File browserFile = new File(dir);
                    for (File file : browserFile.listFiles()) { // 月
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
                    if (file.isFile()) {
                        final String filePath = file.getPath();
                        String totalName = "browser.log-2016-" + filePath.substring(filePath.indexOf("browser") + 8, filePath.indexOf("FlumeData") - 4).replaceAll("\\\\", "-") + ".log";
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
    public void clickNoName() {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\click\\parse\\browser.log-2016-09-12.log";
        ParseClickLog parseClickLog = new ParseClickLog();
        parseClickLog.parseClickNoName(inputPath, ClickType.LINK, 10);
    }
}
