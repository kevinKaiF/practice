package y2016.m08.day20160811;

import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-12 PM04:38
 */
public class ParseOriginLog {
    public static void main(String[] args) {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\parse";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\browser";
        final String dir2 = "C:\\Users\\Administrator\\Desktop\\etl\\browser1";
//        final String type = "b1d760e5806e41e9ad509c8acab30bc5";
        final String type = "ae3bae0cbe534dc7a3ed9e332606de18";

        ParseLog parseLog = new ParseLog();
        parseLog.clearRoot(root);
        parseLog.multiParse(root, type, new ParseDir() {
            @Override
            public List<File> parseSubFile() {
                List<File> fileList = new ArrayList<>();
                for (String dir : new String[]{dir1, dir2}) {
                    File browserFile = new File(dir);
                    fileList.addAll(Arrays.asList(browserFile.listFiles()));
                }
                return fileList;
            }

            @Override
            public Map<String, List<String>> toMap(List<File> subFiles) {
                Map<String, List<String>> map = new LinkedHashMap<>();
                for (final File file : subFiles) {
                    if (file.isFile()) {
                        final String fileName = file.getName();
                        String totalName = fileName.substring(0, fileName.indexOf("_")) + ".log";
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
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\parse";
        String parseIpLog = "C:\\Users\\Administrator\\Desktop\\etl\\ipLog.txt";
        ParseLog parseLog = new ParseLog();
        parseLog.parseFlow(root, parseIpLog, null);
    }

}
