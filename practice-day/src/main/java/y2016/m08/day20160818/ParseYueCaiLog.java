package y2016.m08.day20160818;

import y2016.m08.day20160811.ParseDir;
import y2016.m08.day20160811.ParseLog;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-18 PM01:10
 */
public class ParseYueCaiLog {
    public static void main(String[] args) {
        final String root = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse";
        final String dir1 = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\browser";
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
        ParseLog parseLog = new ParseLog();
        parseLog.parseFlow(root, parseIpLog, null);
    }

    @Test
    public void testReg() {
        final String url = "http://.*yuecai\\.com/*";
        System.out.println("http://www.yuecai.com//".matches(url));
    }

    @Test
    public void testCalcIpCounts() {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse\\browser.log-2016-08-17.log";
        String outPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\calc-2016-08-17.log";
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            writer = new PrintWriter(outPath);
            String line = null;
            List<String> ipList = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split("\t");
                if(arr.length > 6) {
                    String ip = arr[6].trim();
                    if(!ipList.contains(ip)) {
                        ipList.add(ip);
                        writer.println(ip);
                    }
                }
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }

                if(writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSplit() {
        String str = "1471404968327\t\thttp://bj.yuecai.com/support/buyer/\thttp://bj.yuecai.com/topbuyer?SiteID=21\tMozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36\tae3bae0cbe534dc7a3ed9e332606de18\t 117.34.13.54\t124.207.8.210_1457332925826\t采购商服务_企业级互联网采供平台-悦采\tnone\tnone";
        System.out.println(Arrays.toString(str.split("\t")));
    }
}
