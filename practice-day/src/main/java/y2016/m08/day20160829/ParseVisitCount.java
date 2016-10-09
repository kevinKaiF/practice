package y2016.m08.day20160829;

import org.junit.Test;
import scala.actors.threadpool.Arrays;
import y2016.m08.day20160825.Closer;
import y2016.m09.d09.ParseHiveCookie;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-29 AM11:47
 */
public class ParseVisitCount {
    private final Map<String, Integer> typeMap = new HashMap<>();
    private final Map<String, StringBuilder> md5CookieMap = new HashMap<>();
    private final Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com\\.cn|net\\.cn|org\\.cn|gov\\.cn|com\\.hk|com|cn|net|org|biz|info|cc|tv|co|me|name|info|so|tel|mobi|asia)", Pattern.CASE_INSENSITIVE);
    private String separator;
    private MessageDigest md;

    public ParseVisitCount() throws NoSuchAlgorithmException {
        separator = "\t";
        md = MessageDigest.getInstance("md5");
    }

//    public ParseVisitCount(String separator) throws NoSuchAlgorithmException {
//        this.separator = separator;
//        md = MessageDigest.getInstance("md5");
//    }

    /**
     * Parse.
     *
     * @param inputPath  每天的日志目录
     * @param outputPath the output path
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-01 16:47:34
     */
    public void parse(String inputPath, String outputPath) {
        File input = new File(inputPath);
        File[] files = input.listFiles();
        if (files != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(outputPath);
                int count = 0;
                int cookieNull = 0;
                int cookieNotNull = 0;
                List<File> fileList = Arrays.asList(files);
//                Collections.shuffle(fileList);
                for (File file1 : fileList) { // 小时
//                    System.out.println("file name :" + file1.getName());
                    for (File file : file1.listFiles()) { // 数据文件
                        if (file.isFile()) {
                            BufferedReader reader = null;
                            try {
                                reader = new BufferedReader(new FileReader(file));
                                String tmp = null;
                                while ((tmp = reader.readLine()) != null) {
                                    String[] arr = tmp.split(separator);
                                    if (arr.length > 10) {
                                        String url = transferUrl(arr[2]);
                                        String referer = transferUrl(arr[3]);
                                        String md5_url = md5(url);
                                        String md5_refer = md5(referer);
                                        String cookie = arr[7].trim();
                                        String type = arr[5].trim();

                                        if("".equals(type) || "null".equals(type)) {
                                            continue;
                                        }

                                        if (isVC(url, referer)) {
                                            if (typeMap.get(type) == null) {
                                                typeMap.put(type, 1);
                                            } else {
                                                typeMap.put(type, typeMap.get(type) + 1);
                                            }

                                            // 悦采
                                            if("ae3bae0cbe534dc7a3ed9e332606de18".equals(type)) {
                                                count++;
                                            }
                                        } else if (md5CookieMap.get(cookie) == null) {
                                            if (typeMap.get(type) == null) {
                                                typeMap.put(type, 1);
                                            } else {
                                                typeMap.put(type, typeMap.get(type) + 1);
                                            }
                                            if("ae3bae0cbe534dc7a3ed9e332606de18".equals(type)) {
                                                cookieNull++;
                                            }
                                        } else if (md5CookieMap.get(cookie).indexOf(md5_refer) == -1) {
                                            if (typeMap.get(type) == null) {
                                                typeMap.put(type, 1);
                                            } else {
                                                typeMap.put(type, typeMap.get(type) + 1);
                                            }
                                            if("ae3bae0cbe534dc7a3ed9e332606de18".equals(type)) {
                                                cookieNotNull++;
                                            }
                                        }

                                        if (md5CookieMap.get(cookie) == null) {
                                            md5CookieMap.put(cookie, new StringBuilder(128).append(md5_url));
                                        } else if(md5CookieMap.get(cookie).indexOf(md5_url) == -1) {
                                            md5CookieMap.put(cookie, md5CookieMap.get(cookie).append(",").append(md5_url));
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
                        }
                    }
                }

                System.out.println("============== total :" + (count + cookieNull + cookieNotNull));
                System.out.println("count : " + count);
                System.out.println("cookieNull : " + cookieNull);
                System.out.println("cookieNotNull : " + cookieNotNull);
                System.out.println("size : " + md5CookieMap.size());
                write(writer, typeMap);
//                System.out.println("***************************");
//                write(writer, md5CookieMap);
                writer.flush();

//                String cookiePath = "C:\\Users\\Administrator\\Desktop\\cookie.log";
//                ParseHiveCookie parseHiveCookie = new ParseHiveCookie();
//                Map<String, StringBuilder> builderMap = parseHiveCookie.parse(cookiePath, null, md5CookieMap);
//                System.out.println(builderMap.size());
//                System.out.println(builderMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(writer);
            }
        }
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

    private String md5(String sourceStr) {
        md.update(sourceStr.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    private boolean isVC(String url, String referer) {
        if (url == null || referer == null || referer.trim().length() == 0) {
            return true;
        }
        Matcher m1 = p.matcher(url);
        Matcher m2 = p.matcher(referer);
        if (m1.find() && m2.find()) {
            if (m1.group().equals(m2.group())) {
                String find = m1.group();
//                if (url.substring(0, url.indexOf(find)).equals(referer.substring(0, referer.indexOf(find)))) {
//                    return false;
//                } else {
//                    return true;
//                }
                return false;
            }
        }

        try {
            URL u1 = new URL(url);
            URL u2 = new URL(referer);
            if (u1.getHost().equals(u2.getHost()) && u1.getPort() == u2.getPort()) {
                return false;
            }
        } catch (MalformedURLException e) {
            return true;
        }
        return true;
    }

    private void write(PrintWriter writer, Map typeMap) {
        if (typeMap != null) {
            writer.println("================= ");
            for (Object key : typeMap.keySet()) {
                writer.println(key + ":" + typeMap.get(key));
            }
            writer.println();
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\vcLOG";
//        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\browser\\09\\02";
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\dw\\browser\\09\\02";
        String outputPath = "C:\\Users\\Administrator\\Desktop\\etl\\vcLog.txt";
        ParseVisitCount visitCount = new ParseVisitCount();
        visitCount.parse(inputPath, outputPath);
    }

    @Test
    public void testMd5() throws NoSuchAlgorithmException {
        ParseVisitCount count = new ParseVisitCount();
//        System.out.println(count.md5("test"));
//        System.out.println(count.md5("test"));
        System.out.println(count.md5("http://www.yuecai.com/"));
    }

    @Test
    public void testByte() {
        byte b = -122;
        System.out.println(b & 0xFF);
        System.out.println();
    }

    @Test
    public void testFormat() {
        long start = System.currentTimeMillis();
        int a = 2;
        String s = null;
        for (int i = 0; i < 10000; i++) {
            s = String.format("%02x", a); // StringFormat的效率还是有点低
        }
        long end = System.currentTimeMillis();
        System.out.println("time1 : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            if (a < 16) {
                s = "0" + a;
//                s = MessageFormat.format("{0}", a);
            }
        }
        end = System.currentTimeMillis();
        System.out.println("time2 : " + (end - start));

    }

    @Test
    public void testAnd() {
        byte b = -128;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int tmp = b & 0xFF;
        }
        long end = System.currentTimeMillis();
        System.out.println("time1 : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            if (b < 0) {
                int tmp = b + 256;
            }
        }
        end = System.currentTimeMillis();
        System.out.println("time2 : " + (end - start));

    }

    @Test
    public void testReg() {
//        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com\\.cn|net\\.cn|org\\.cn|gov\\.cn|com\\.hk|com|cn|net|org|biz|info|cc|tv|co|me|name|info|so|tel|mobi|asia)", Pattern.CASE_INSENSITIVE);
        Pattern p = Pattern.compile("[^.]*?\\.(com\\.cn|net\\.cn|org\\.cn|gov\\.cn|com\\.hk|com|cn|net|org|biz|info|cc|tv|co|me|name|info|so|tel|mobi|asia)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher("http-3A-2F-2Fwww.so.com-2Flink-3Furl-3Dhttp-253A-252F-252Fwww.yuecai.com-252F-26q-3D-25E4-25BC-2581-25E4-25B8-259A-25E7-25BA-25A7-25E4-25BA-2592-25E8-2581-2594-25E7-25BD-2591-25E5-25B9-25B3-25E5-258F-25B0-26ts-3D1463097257-26t-3Ddc35a165966a401fa7929dce91c90f5-26src-3Dhaosou");

        while (matcher.find()) {
            // groupCount不包含group(0)
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println(matcher.group(i));
            }
        }
    }

}
