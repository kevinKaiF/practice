package y2017.m03.d28;

import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/28
 */
public class TestURL {
    @Test
    public void testUrl() throws MalformedURLException {
        URL url = new URL(null, "dubbo://locahost:8080", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                return null;
            }
        });
        System.out.println(url);
    }

    @Test
    public void testHttpUrl(){
        BufferedReader reader = null;
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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

}
