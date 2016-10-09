package y2016.m08.day20160831.forkAjoin;


import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-31 PM05:41
 */
public class Document {
    private List<String> lines;

    public Document(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    public static Document fromFile(File file) {
        BufferedReader reader = null;
        try {
            List<String> lines = new ArrayList<>();
            reader = new BufferedReader(new FileReader(file));
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                lines.add(tmp);
            }

            return new Document(lines);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }

        return null;
    }
}
