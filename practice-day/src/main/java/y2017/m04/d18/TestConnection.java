package y2017.m04.d18;

import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/18
 */
public class TestConnection {
    @Test
    public void testConnection() {
        BufferedReader reader = null;
        try {
            InputStream inputStream = getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
    }

    private InputStream getInputStream() throws IOException {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("https://www.baidu.com");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            return inputStream;
        } finally {
            // 不能这么写，inputStream会被关闭，无法读取流
            if (urlConnection != null) {
//                urlConnection.disconnect();
            }
        }
    }
}
