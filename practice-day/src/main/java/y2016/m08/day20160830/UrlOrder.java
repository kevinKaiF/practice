package y2016.m08.day20160830;

import y2016.m08.day20160825.Closer;
import org.junit.Test;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-30 PM04:45
 */
public class UrlOrder {
    public void count(String input, String out) {
        write(out, getUrlOrderMap(input), "count");
    }

    private void write(String path, Map<String, Integer> map, String title) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
            List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });

            for (Map.Entry<String, Integer> entry : list) {
                if (entry.getValue() != 0) {
                    writer.println("====================================== " + title);
                    writer.println("url : " + entry.getKey());
                    writer.println("pv : " + entry.getValue());
                }
            }

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(writer);
        }
    }

    public void compare(String baiduPath, String ebnewPath, String baiduOutpath, String ebnewOutpath, String output) throws UnsupportedEncodingException {
        Map<String, Integer> baiduMap = getCountMap(baiduPath);
        Map<String, Integer> ebnewMap = getCountMap(ebnewPath);
        Map<String, Integer> result = new HashMap<>();
        Map<String, Integer> tmp = new HashMap<>(ebnewMap);

        printCount(ebnewMap);
        printCount(baiduMap);

        for (String key : tmp.keySet()) {
            String newKey = null;
            int len = key.length();
            if (baiduMap.get(key) != null) {
                result.put(key, ebnewMap.get(key) - baiduMap.get(key));
                ebnewMap.remove(key);
                baiduMap.remove(key);
//            } else if (len > 150) { // 处理Url过长的问题，因为百度对url做了截断处理，最长不超过150
//                newKey = key.substring(0, 75) + '…' + key.substring(len - 74, len);
//                if(baiduMap.get(newKey) != null) {
//                    try {
//                        result.put(key, ebnewMap.get(key) - baiduMap.get(newKey));
//                        ebnewMap.remove(key);
//                        baiduMap.remove(newKey);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                }
            } else if (key.endsWith("/")) { // 百度url末尾没有/
                newKey = key.substring(0, len - 1);
                if (baiduMap.get(newKey) != null) {
                    try {
                        result.put(key, ebnewMap.get(key) - baiduMap.get(newKey));
                        ebnewMap.remove(key);
                        baiduMap.remove(newKey);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }
        tmp.clear();
        tmp = null;

        System.out.println("================= count");
        write(output, result, "count");
        printCount(result);

        System.out.println("================= ebnewMap");
        printCount(ebnewMap);
        write(ebnewOutpath, ebnewMap, "ebnewMap");

        System.out.println("================= baiduMap");
        printCount(baiduMap);
        write(baiduOutpath, baiduMap, "baiduMap");
    }

    private void printCount(Map<String, Integer> result) {
        int count = 0;
        for (String key : result.keySet()) {
            count += result.get(key);
        }
        System.out.println("count : " + count);
    }

    private void printMap(Map<String, Integer> ebnewMap) {
        for (String key : ebnewMap.keySet()) {
            System.out.println("------------------");
            System.out.println("url : " + key);
            System.out.println("pv : " + ebnewMap.get(key));
        }
    }

    private Map<String, Integer> getUrlOrderMap(String input) {
        BufferedReader reader = null;
        Map<String, Integer> map = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(input));
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                String[] arr = tmp.split("\t");
                String url = arr[2];
                if (map.get(url) == null) {
                    map.put(url, 1);
                } else {
                    map.put(url, map.get(url) + 1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
        return map;
    }

    private Map<String, Integer> getCountMap(String input) {
        BufferedReader reader = null;
        Map<String, Integer> map = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(input));
            String tmp = null;
            String prev = "";
            int count = 0;
            while ((tmp = reader.readLine()) != null) {
                if (tmp.indexOf("pv :") > -1) {
                    String url = transferUrl(prev.substring(prev.indexOf(": ") + 1).trim());
                    url = decodeFullText(url);
                    url = trimUrl(url);

                    Integer pv = Integer.parseInt(tmp.substring(tmp.indexOf(": ") + 1).trim());
                    count += pv;
                    if (map.get(url) == null) {
                        map.put(url, pv);
                    } else {
                        map.put(url, map.get(url) + pv);
                    }
                }
                prev = tmp;
            }
            System.out.println("co :" + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
        return map;
    }

    private String decodeFullText(String url) {
        String target = "fullText";
        if (url.indexOf(target) > -1) {
            try {
                String keyword = url.substring(url.indexOf(target) + target.length() + 1);
                if(keyword.indexOf('…') == -1) {
                    if (keyword.indexOf("%") > -1) {
                        try {
                            keyword = URLDecoder.decode(keyword, "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(!Charset.forName("GBK").newEncoder().canEncode(keyword)) {
                            keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
                        }
                    }
                    url = url.substring(0, url.indexOf(target)) + target + "=" + keyword;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    private String trimUrl(String url) {
        if(url.indexOf("#") > -1) {
            url = url.substring(0, url.indexOf("#"));
        }

        if (url.length() > 150) {
            url = url.substring(0, 75) + '…' + url.substring(url.length() - 74, url.length());
        }
        return url;
    }

    public String transferUrl(String url) {
        if (url == null || url == "") {
            return url;
        }

        String http = "http-3A-2F-2F";
        String https = "https-3A-2F-2F";
        if (url.indexOf(http) > -1 || url.indexOf(https) > -1) {
            String[] arr = url.split("=");
            StringBuilder builder = new StringBuilder(url.length());
            // 处理http头部
            builder.append(arr[0].replace("-3A", ":").replace("-2F", "/"));

            for (int i = 1; i < arr.length; i++) {
                // 处理url中参数
                builder.append("=")
                        .append(arr[i].replace("-3A", "%3A").replace("-2F", "%2F"));
            }
            return builder.toString();
        } else {
            return url;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String month = "08";
        String day = "24";
        String intput = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse\\browser.log-2016-" + month + "-" + day + ".log";
        String output = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\" + month + "\\" + day + "\\yuecaiLog.txt";
        UrlOrder urlOrder = new UrlOrder();
        urlOrder.count(intput, output);

        String baiduPath = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\" + month + "\\" + day + "\\baiduLog.txt";
        String ebnewPath = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\" + month + "\\" + day + "\\yuecaiLog.txt";
        String baiduOutpath = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\"+ month + "\\" + day + "\\baiduOutLog.txt";
        String ebnewOutpath = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\"+ month + "\\" + day + "\\yuecaiOutLog.txt";
        String output2 = "C:\\Users\\Administrator\\Desktop\\bdVSeb\\"+ month + "\\" + day + "\\countLog.txt";
        urlOrder.compare(baiduPath, ebnewPath, baiduOutpath, ebnewOutpath, output2);
    }

    @Test
    public void testDecode() throws UnsupportedEncodingException {
        String s = "http://www.yuecai.com/purchase/search?SiteID=21&fullText=%E7%85%A4%E7%82%AD";
        System.out.println(URLDecoder.decode(s, "UTF-8"));
    }

    @Test
    public void testDecode2() throws UnsupportedEncodingException {
//        String s = "ç\u0094µç¼\u0086";
        String s = "你好";
        System.out.println(Charset.forName("GBK").newEncoder().canEncode(s));
        System.out.println(URLEncoder.encode(new String(s.getBytes("ISO-8859-1")), "UTF-8"));

        System.out.println(((int) '!'));
        System.out.println(((int) '~'));

        for (int i = 127; i < Integer.MAX_VALUE; i++) {
            System.out.println(((char) i));
        }
    }
}
