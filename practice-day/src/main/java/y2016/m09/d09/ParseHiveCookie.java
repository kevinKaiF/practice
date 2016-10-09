package y2016.m09.d09;

import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-09 AM11:24
 */
public class ParseHiveCookie {
    public Map<String, StringBuilder> parse(String inputPath, String outputPath, Map<String, StringBuilder> cookieMap) {
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            if (outputPath != null) {
                writer = new PrintWriter(outputPath);
            }

            reader = new BufferedReader(new FileReader(inputPath));
            String identifierReg = "\\d+\\.\\d+\\.\\d+\\.\\d+_\\d+";
            String temp = null;
            int count = 0;
            while ((temp = reader.readLine()) != null) {
                int index = temp.indexOf("column=info:url");
                if (index != -1) {
                    String identifier = temp.substring(0, index).trim();
                    if (identifier.matches(identifierReg)) {
                        if(cookieMap.containsKey(identifier)) {
                            cookieMap.remove(identifier);
                            count++;
                        } else {
                            System.out.println(identifier);
                        }
                    }
                }
            }

            if (writer != null) {
                writer.flush();
            }

            System.out.println("contains :" + count);

            return cookieMap;
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
}
