package y2016.m08.day20160829;

import y2016.m08.day20160825.Closer;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-29 PM02:17
 */
public class ParseUserId {
    private String separator;

    public ParseUserId() {
        separator = "\t";
    }

    public void parse(String inputPath, String outputPath) {
        File input = new File(inputPath);
        File[] files = input.listFiles();
        if (files != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(outputPath);
                writer.println(String.format("%1$-20s%2$s", "userid", "url"));
                writer.println();
                Set<String> urls = new HashSet<>();
                for (File file : files) {
                    if (file.isFile()) {
                        BufferedReader reader = null;
                        try {
                            int count = 0;
                            int pv = 0;
                            reader = new BufferedReader(new FileReader(file));
                            String tmp = null;
                            while ((tmp = reader.readLine()) != null) {
                                String[] arr = tmp.split(separator);
                                pv++;
                                if ("".equals(arr[1]) || "null".equalsIgnoreCase(arr[1])) {
                                    count++;
                                } else {
                                    if(urls.add(arr[2])) {
                                        writer.println(String.format("%-20s", arr[1]) + arr[2]);
                                    }
                                }
                            }
                            System.out.println("=========== " + file.getName() + "     count : " + count + ", pv : " + pv);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            Closer.closeQuietly(reader);
                        }
                    }
                }

                writer.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(writer);
            }
        }
    }

    public static void main(String[] args) {
        String inputPath = "C:\\Users\\Administrator\\Desktop\\etl\\hdfs\\parse";
        String outputPath = "C:\\Users\\Administrator\\Desktop\\etl\\userIdLog.txt";
        ParseUserId parseUserId = new ParseUserId();
        parseUserId.parse(inputPath, outputPath);
    }

    @Test
    public void testChar() {
        System.out.println(Integer.toHexString(':'));
    }
}
