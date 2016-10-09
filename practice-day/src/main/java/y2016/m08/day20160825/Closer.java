package y2016.m08.day20160825;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-25 PM04:04
 */
public class Closer {
    public static void closeQuietly(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
