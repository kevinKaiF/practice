package y2016.m08.day20160811;

import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-11 AM09:22
 */
public class ParseLog {
    final private String separator;
    private static final String TAB = "\t";

    public ParseLog() {
        separator = TAB;
    }

    public ParseLog(String separator) {
        this.separator = separator;
    }

    public void parse(String dir, String root, String url) {
        File browserFile = new File(dir);
        File[] files = browserFile.listFiles();
        if (files != null && files.length > 0) {
            for (File temp : files) { // 这里只有一层目录
                if (temp.isFile()) {
                    String name = temp.getName();
                    String parseName = name.substring(0, name.indexOf("_")) + ".log";
                    File parsedFile = new File(root, parseName);
                    BufferedReader reader = null;
                    PrintWriter writer = null;
                    try {
                        reader = new BufferedReader(new FileReader(temp));
                        writer = new PrintWriter(new FileOutputStream(parsedFile, true));
                        String str;
                        while ((str = reader.readLine()) != null) {
                            String[] strs = str.split(separator);
                            try {
                                if (url.equals(strs[2])) {
                                    writer.println(str);
                                }
                            } catch (Exception e) {
                                System.out.println(str + "  ---------------------------");
                            }
                        }
                        writer.flush();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        Closer.closeQuietly(reader);
                        Closer.closeQuietly(writer);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 多线程将每天24小时的日志合并成当天的日志.
     *
     * @param root the root
     * @param type the type
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-11 17:33:05
     */
    public void multiParse(final String root, final String type, ParseDir parseDir) {
        List<File> fileList = parseDir.parseSubFile();
        if (fileList != null && fileList.size() > 0) {
            // 将文件名处理成map形式，以总文件名为key,以小文件名为value
            final Map<String, List<String>> map = parseDir.toMap(fileList);
            final ExecutorService executorService = Executors.newFixedThreadPool(4);

            for (final String totalName : map.keySet()) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<String> subNames = map.get(totalName);
                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(new FileOutputStream(new File(root, totalName), true));
                            for (String name : subNames) {
                                BufferedReader reader = null;
                                try {
                                    reader = new BufferedReader(new FileReader(name));
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        String[] strs = str.split(separator);
                                        if (strs != null && strs.length > 10) {
                                            if (strs[5].contains(type)) {
                                                writer.println(str);
                                            }
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    Closer.closeQuietly(reader);
                                }

                                System.out.println("finish : " + name);
                            }

                            writer.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            Closer.closeQuietly(writer);
                            executorService.shutdown();
                        }
                    }
                });
            }
        }
    }

    private String countIp(String[] record, Set<String> cache) {
        String ip = record[6];  // ip
        cache.add(ip);
        return ip;
    }

    private String dumpIp(String prevName, Set<String> cache) {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        sb.append(lineSeparator)
                .append("==================" + prevName)
                .append(lineSeparator)
                .append("ip : " + cache.size() + "  " + cache.toString());
        System.out.println(sb);
        cache.clear();
        return sb.toString();
    }


    public void clearRoot(String root) {
        // clear root
        File rootFile = new File(root);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }

        File[] subFiles = rootFile.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File file : rootFile.listFiles()) {
                file.delete();
            }
        }
    }

    //    @Test
    public void test() {
        Pattern pattern = Pattern.compile("\t\\d+\\.\\d+\\.\\d+\\.\\d+\t");
        Matcher matcher = pattern.matcher("1420333600529\t\thttp://tj.ebnew.com/index.html\t\tMozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\tb1d760e5806e41e9ad509c8acab30bc5\t124.207.8.210\t211.151.182.214|1418364099857\t%E7%BD%91%E7%AB%99%E7%BB%9F%E8%AE%A1%E5%88%86%E6%9E%90%E7%B3%BB%E7%BB%9F\t%E7%BD%91%E7%AB%99%E7%BB%9F%E8%AE%A1%E5%88%86%E6%9E%90%E7%B3%BB%E7%BB%9F\t%E7%BD%91%E7%AB%99%E7%BB%9F%E8%AE%A1%E5%88%86%E6%9E%90%E7%B3%BB%E7%BB%9F");
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    /**
     * 统计PV,UV,IP.
     *
     * @param root       the root
     * @param parseIpLog the parse ip log
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-08-16 09:22:30
     */
    public void parseFlow(String root, String parseIpLog, String ignoredIp) {
        File rootFile = new File(root);
        File[] files = rootFile.listFiles();
        if (files != null && files.length > 0) {
            Set<String> ipCache = new HashSet<>();
            Set<String> cookieSet = new HashSet<>();
            PrintWriter write = null;
            try {
                if (ignoredIp == null) {
                    ignoredIp = "";
                }

                write = new PrintWriter(parseIpLog);
                for (File temp : files) {
                    if (temp.isFile()) {
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new FileReader(temp));
                            String str;
                            int pv = 0;
                            while ((str = reader.readLine()) != null) {
                                String[] arr = str.split(separator);
                                if (ignoredIp.indexOf(arr[6]) == -1 /*&& arr.length > 10*/) {
                                    countIp(arr, ipCache);
                                    countUv(arr, cookieSet);
                                    pv++;
                                }
                            }

                            write.println(dumpIp(temp.getName(), ipCache));
                            write.println(dumpPv(pv));
                            write.println(dumpUv(cookieSet));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                write.flush();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (write != null) {
                    write.close();
                }
            }
        }
    }

    private String dumpUv(Set<String> cookieSet) {
        int uv = 0;
        if (cookieSet != null) {
            uv = cookieSet.size();
        }
        cookieSet.clear();
        return "uv : " + uv;
    }

    private void countUv(String[] record, Set<String> cookieSet) {
        String cookie = record[7];
        cookieSet.add(cookie);
    }

    private String dumpPv(int pv) {
        return "pv : " + pv;
    }

    /**
     * 用户访问页面分析.
     *
     * @param inputPath the input path
     * @param topNum    the top num
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-13 16:38:10
     */
    public void parseUserVisitPage(String inputPath, int topNum) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            Map<String, Integer> pageVisitMap = new HashMap<>();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                String[] arr = tmp.split(separator);
                String url = arr[2];
                Integer count = pageVisitMap.get(url);
                if (count == null) {
                    pageVisitMap.put(url, 1);
                } else {
                    pageVisitMap.put(url, count + 1);
                }
            }

            print(pageVisitMap, topNum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
    }

    private void print(Map<String, Integer> pageVisitMap, int topNum) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(pageVisitMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        for (int i = 0, len = entryList.size(); i < len && i < topNum; i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * 用户访问路径分析.
     *
     * @param dayPath 每一天日志路径
     * @param type    the type
     * @param topNum
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-13 16:48:53
     */
    public void parseUserVisitPath(String dayPath, String type, int topNum) {
        ParseDir parseDir = buildParseDir(dayPath);
        List<File> fileList = parseDir.parseSubFile();
        if (fileList != null && fileList.size() > 0) {
            Map<String, Integer> visitPathMap = new HashMap<>();
            // 将文件名处理成map形式，以总文件名为key,以小文件名为value
            Map<String, List<String>> map = parseDir.toMap(fileList);
            // 遍历每个时刻
            for (final String totalName : map.keySet()) {
                Map<String, List<UrlAndRefer>> visitTrackMap = parseVisitPathPerHour(map.get(totalName), type);
                doParseVisitPath(visitPathMap, visitTrackMap);
                visitTrackMap.clear();
                visitTrackMap = null;
            }

            print(visitPathMap, topNum);
            countPV(visitPathMap);
        }
    }

    private void countPV(Map<String, Integer> visitPathMap) {
        int pv = 0;
        for (String track : visitPathMap.keySet()) {
            pv += countJump(track) * visitPathMap.get(track);
        }
        System.out.println("================== pv : " + pv);
    }

    private int countJump(String track) {
        return track.split("\\^\\^").length;
    }

    /**
     * 解析每个时钟的日志数据.
     * NOTE : 按每小时处理是有原因的，mapreduce是按每小时执行，为此按小时模拟计算
     *
     * @param subNames the sub names
     * @param type     the type
     * @return the map
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-14 09:55:22
     */
    private Map<String, List<UrlAndRefer>> parseVisitPathPerHour(List<String> subNames, String type) {
        // 用户访问轨迹
        int count = 0;
        Map<String, List<UrlAndRefer>> visitTrackMap = new HashMap<>();
        for (String name : subNames) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(name));
                String str = null;
                while ((str = reader.readLine()) != null) {
                    String[] arr = str.split(separator);
                    if (arr != null) {
                        if (arr[5].contains(type)) {
                            count++;
                            final String url = arr[2];
                            final String refer = arr[3];
                            String identifier = arr[7];
                            if (!url.equals(refer)) {
                                List<UrlAndRefer> urls = visitTrackMap.get(identifier);
                                if (urls == null) {
                                    visitTrackMap.put(identifier, new ArrayList<UrlAndRefer>() {
                                        {
                                            add(new UrlAndRefer(url, refer));
                                        }
                                    });
                                } else {
                                    urls.add(new UrlAndRefer(url, refer));
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(reader);
            }

            System.out.println("finish : " + name);
        }

        System.out.println("count : " + count);

        return visitTrackMap;
    }

    private void doParseVisitPath(Map<String, Integer> visitPathMap, Map<String, List<UrlAndRefer>> visitTrackMap) {
        String separator = "^^";
        for (String identifier : visitTrackMap.keySet()) {
            // 清空
            String prevUrl = null;
            String curRefer = null;
            String trackPath = "";
            int count = 0;
            for (UrlAndRefer urlAndRefer : visitTrackMap.get(identifier)) {
                curRefer = urlAndRefer.getRefer();
                // 首次访问
                if (prevUrl == null) {
                    trackPath += urlAndRefer.getUrl() + separator;
                    prevUrl = urlAndRefer.getUrl();
                    count++;
                } else {
                    if (prevUrl.equals(curRefer)) {
                        trackPath += urlAndRefer.getUrl() + separator;
                        prevUrl = urlAndRefer.getUrl();
                        count++;
                    } else { // 另一个页面
                        // 记录下统计结果
                        Integer visitCount = visitPathMap.get(trackPath);
                        if (visitCount == null) {
                            visitPathMap.put(trackPath, 1);
                        } else {
                            visitPathMap.put(trackPath, visitCount + 1);
                        }

                        // 清空
                        prevUrl = null;
                        trackPath = "";
                        count = 0;

                        // 记录本次
                        trackPath += urlAndRefer.getUrl() + separator;
                        prevUrl = urlAndRefer.getUrl();
                        count++;

                    }
                }
            }

            if (prevUrl != null) {
                Integer visitCount = visitPathMap.get(trackPath);
                if (visitCount == null) {
                    visitPathMap.put(trackPath, 1);
                } else {
                    visitPathMap.put(trackPath, visitCount + 1);
                }
            }
        }


    }

    private ParseDir buildParseDir(final String dayPath) {
        return new ParseDir() {
            @Override
            public List<File> parseSubFile() {
                List<File> fileList = new ArrayList<>();
                File browserFile = new File(dayPath);
                for (File file : browserFile.listFiles()) { // 时
                    fileList.addAll(Arrays.asList(file.listFiles()));
                }
                return fileList;
            }

            @Override
            public Map<String, List<String>> toMap(List<File> subFiles) {
                Map<String, List<String>> map = new LinkedHashMap<>();
                for (final File file : subFiles) {
                    if (file.isFile()) {
                        final String filePath = file.getPath();
                        String totalName = "browser.log-2016-" + filePath.substring(filePath.indexOf("browser") + 8, filePath.indexOf("FlumeData") - 1).replaceAll("\\\\", "-") + ".log";
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
        };
    }

    public class UrlAndRefer {
        private String url;
        private String refer;

        public UrlAndRefer(String url, String refer) {
            this.refer = refer;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String getRefer() {
            return refer;
        }

        @Override
        public String toString() {
            return "UrlAndRefer{" +
                    "url='" + url + '\'' +
                    ", refer='" + refer + '\'' +
                    '}';
        }
    }


}
