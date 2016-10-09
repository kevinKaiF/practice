package y2016.m08.day20160825;

import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-25 PM04:35
 */
public class ParseOasUserWebflow {
    public Set<String> parse(boolean writeToFile) {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\oas_user_webflow.txt";
        String outputPath = "C:\\Users\\Administrator\\Desktop\\etl\\oas_user_webflow_parse.txt";

        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            if (writeToFile) {
                writer = new PrintWriter(outputPath);
            }

            reader = new BufferedReader(new FileReader(inputPath));
            Set<String> cache = new LinkedHashSet<>();
            String identifierReg = "\\d+\\.\\d+\\.\\d+\\.\\d+_\\d+";
            long maxTimeStamp = 0;
            String temp = null;

            while ((temp = reader.readLine()) != null) {
                int index = temp.indexOf("column=info:userid");
                if (index != -1) {
                    String identifier = temp.substring(0, index).trim();
                    if (identifier.matches(identifierReg)) {
                        if (!cache.contains(identifier)) {
                            cache.add(identifier);
                            if (writeToFile) {
                                writer.println(identifier);
                            }
                        }

                        String timeStamp = temp.substring(temp.indexOf("timestamp"), temp.indexOf(", value"));
                        long currentStamp = Long.valueOf(timeStamp.split("=")[1]);
                        if (currentStamp > maxTimeStamp) {
                            maxTimeStamp = currentStamp;
                        }
                    }
                }
            }

            System.out.println("maxTimeStamp : " + maxTimeStamp);
            if (writeToFile) {
                writer.flush();
            }
            return cache;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
            Closer.closeQuietly(writer);
        }

        return null;
    }

    @Test
    public void testCalcMaxTimeStamp() {
        parse(false);
    }

    @Test
    public void testReg() {
        System.out.println("94.200.107.70_1423030524198".matches("\\d+\\.\\d+\\.\\d+\\.\\d+_\\d+"));
        System.out.println("1423030524198".matches("\\d+"));
    }

    @Test
    public void testCalcUV() {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hive\\oas_browser_second_log.log";
        Set<String> oldUV = new LinkedHashSet<String>(parse(false)) {
            @Override
            public boolean contains(Object o) {
                String ip = ((String) o).substring(0, ((String) o).indexOf("_"));
                Iterator<String> it = iterator();
                while (it.hasNext()) {
                    String cur = it.next();
                    if (ip.equals(cur.substring(0, cur.indexOf("_"))))
                        return true;
                }
                return false;
            }
        };

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputPath));
            int count = 0;
            Set<String> cache = new LinkedHashSet<>();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                String[] strs = temp.split("\u0001");
                try {
                    String identifier = strs[7].trim();
                    if (!oldUV.contains(identifier) && !cache.contains(identifier)) {
                        count++;
                        cache.add(identifier);
                    }
                } catch (Exception e) {
                    System.out.println("=========" + temp);
                }
            }

            System.out.println("count : " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
    }

}
