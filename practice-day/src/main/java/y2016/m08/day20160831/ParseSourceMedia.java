package y2016.m08.day20160831;


import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-31 PM02:52
 */
public class ParseSourceMedia {
    public void parse(String inputPath, String outputPath) {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            writer = new PrintWriter(outputPath);
            reader = new BufferedReader(new FileReader(inputPath));
            Map<String, Integer> mediaCountMap = new HashMap<>();
            Map<String, Integer> filteredMediaCountMap = new HashMap<>();

            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                String[] arr = tmp.split("\t");
                if(arr.length > 11) {
                    String media = arr[11];
                    String browser = arr[18];
                    if(mediaCountMap.get(media) == null) {
                        mediaCountMap.put(media, 1);
                    } else {
                        mediaCountMap.put(media, mediaCountMap.get(media) + 1);
                    }

                    if(browser.indexOf("Spider") == -1) {
                        if(filteredMediaCountMap.get(media) == null) {
                            filteredMediaCountMap.put(media, 1);
                        } else {
                            filteredMediaCountMap.put(media, filteredMediaCountMap.get(media) + 1);
                        }
                    }
                }
            }

            write(writer, mediaCountMap);
            write(writer, filteredMediaCountMap);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
            Closer.closeQuietly(writer);
        }


    }

    private void write(PrintWriter writer, Map<String, Integer> mediaCountMap) {
        if(mediaCountMap != null) {
            writer.println("================= ");
            for (String key : mediaCountMap.keySet()) {
                writer.println("source : " + key);
                writer.println("pv : " + mediaCountMap.get(key));
            }
        }
    }

    public static void main(String[] args) {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\hive\\08\\30\\data.txt";
        String outputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\sourceLog.txt";
        ParseSourceMedia media = new ParseSourceMedia();
        media.parse(inputPath, outputPath);
    }
}
