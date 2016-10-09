package y2016.m09.d13;

import com.alibaba.dubbo.common.json.ParseException;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import y2016.m08.day20160811.ParseDir;
import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-13 AM10:17
 */
public class ParseClickLog {
    final private String separator;
    private static final String TAB = "\t";

    public ParseClickLog() {
        separator = TAB;
    }

//    public ParseClickLog(String separator) {
//        this.separator = separator;
//    }

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
                                            if (strs[2].contains(type)) {
                                                writer.println(str);
                                            }
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (reader != null) {
                                            reader.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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

    public void parseClickNoName(String inputPath, final ClickType clickType, int topNum) {
        BufferedReader reader = null;
        try {
            Map<String, Map<String, Integer>> clickCountMap = new HashMap<>();
            reader = new BufferedReader(new FileReader(inputPath));
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                final String[] arr = tmp.split(separator);
                if (arr.length > 10 && clickType.getTag().equals(arr[4]) /*&& StringUtils.isNotEmpty(arr[1])*/) {
                    String key = buildKey(arr, clickType);
                    final String tag = get(arr[5], clickType.getAttr());
                    Map<String, Integer> itemMap = clickCountMap.get(key);
                    if (itemMap == null) {
                        clickCountMap.put(key, new HashMap<String, Integer>() {
                            {
                                put(tag, 1);
                            }
                        });
                    } else {

                        itemMap.put(tag, (itemMap.get(tag) == null ? 0 : itemMap.get(tag)) + 1);
                    }
                }
            }

            print(clickCountMap, topNum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }

    }

    private void print(Map<String, Map<String, Integer>> clickCountMap, int topNum) {
        List<Map.Entry<String, Map<String, Integer>>> entryList = new ArrayList<>(clickCountMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Map<String, Integer>>>() {
            @Override
            public int compare(Map.Entry<String, Map<String, Integer>> o1, Map.Entry<String, Map<String, Integer>> o2) {
                return o2.getValue().size() - o1.getValue().size();
            }
        });

        for (int i = 0, len = entryList.size(); i < len && i < topNum; i++) {
            Map.Entry<String, Map<String, Integer>> item = entryList.get(i);
            System.out.println("==================== " + item.getKey());
            doPrint(item.getValue());
        }
    }

    private void doPrint(Map<String, Integer> clickCountMap) {
        List<Map.Entry<String, Integer>> values = new ArrayList<>(clickCountMap.entrySet());
        Collections.sort(values, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        print(values);
    }

    private <T extends Map.Entry> void print(Collection<T> collection) {
        for (T t : collection) {
            try {
                System.out.println(t.getKey().toString() + " : " + t.getValue().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String buildKey(String[] arr, ClickType clickType) throws ParseException {
        String key = null;
        switch (clickType) {
            case LINK:  // t1.type,t1.url,t1.userid,t1.identifier,t1.info['innerHTML'];
                key = join(arr[2], arr[3], arr[9]/*arr[1], arr[10], get(arr[5], "innerHTML")*/);
                break;
            case BUTTON: // t1.type,t1.url,t1.userid,t1.identifier,t1.info['value'];
                key = join(arr[2], arr[3], arr[9]/*arr[1], arr[10], get(arr[5], "value")*/);
                break;
            case IMAGE: // t1.type,t1.url,t1.userid,t1.identifier,t1.info['src'];
                key = join(arr[2], arr[3], arr[9]/*arr[1], arr[10], get(arr[5], "src")*/);
                break;
            case SEARCH: // t1.type,t1.url,t1.userid,t1.identifier,t1.info['value'];
                key = join(arr[2], arr[3], arr[9]/*arr[1], arr[10], get(arr[5], "value")*/);
                break;
            default:
                break;
        }
        return key;
    }

    private String get(String str, String key){
        String[] split = str.split("\\|");
        for (String item : split) {
            if (item.startsWith(key + ":")) {
                return item.substring(item.indexOf(":") + 1);
            }
        }
        return null;
    }

    private String join(String... param) {
        return StringUtils.join(param, "_");
    }

    @Test
    public void testJoin() {
        System.out.println(join("2", "3"));
    }

    @Test
    public void testGet() {
        String str = "id:null|name:null|class:buttona|title:|href:http://home.yuecai.com/index.htm|innerHTML:进入我的首页|tj_element:false";
        System.out.println(get(str, "innerHTML"));
    }

}
