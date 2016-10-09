package y2016.m08.day20160812;

import y2016.m08.day20160811.ParseDir;
import y2016.m08.day20160811.ParseLog;
import y2016.m08.day20160825.Closer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * 解析hdfs中的日志
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-12 PM04:14
 */
public class ParseHDFSLog {
    public static void main(String[] args) {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\browser";
        final String type = "b1d760e5806e41e9ad509c8acab30bc5";  // 日志系统
//        final String type = "ae3bae0cbe534dc7a3ed9e332606de18"; // 悦采
//        final String type = "9b2978902fa64498a2d3330d1f49962d"; // 比联网-广告
//        final String type = "35b2b795d689428f848d1edf059a9b7a"; // 比联网2.0
//        final String type = "0a6ce57d790d4e3bb00456516cf406d1"; // 老必联

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
    public void testParseFlow() {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse";
        String parseIpLog = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\ipLog.txt";
        String ignoredIp = "124.207.8.210,211.151.182.214,219.143.69.27,113.57.165.74";
        ParseLog parseLog = new ParseLog();
        parseLog.parseFlow(root, parseIpLog, ignoredIp);
    }

    @Test
    public void testNewUv() {
        Set<String> uv = new HashSet<>();
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse\\browser.log-2016-08-23.log";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                uv.add(temp.split("\t")[7]);
            }

            System.out.println(uv);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
    }
}
