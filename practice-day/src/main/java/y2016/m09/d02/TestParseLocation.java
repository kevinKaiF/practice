package y2016.m09.d02;

import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.*;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-02 AM11:37
 */
public class TestParseLocation {
    @Test
    public void test() {
        IpSeeker seeker = new IpSeeker();
        String country = seeker.getCountry("101.204.247.152");
        String arr[] = AdressUtils.parseCountryCity(country);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testRelativePath() {
        File file = new File("qqwry.dat");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testURL() {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.baidu.com");
            URLConnection connection = url.openConnection();
            reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                System.out.println(tmp);
            }
            FileNameMap fileNameMap = connection.getFileNameMap();
            System.out.println();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(reader);
        }
    }

    @Test
    public void testProp() {
        Properties prop = new Properties();
        FileInputStream inputStream = null;
//        File file = new File(System.getProperty("java.home") + File.separator + "lib" + File.separator + "content-types.properties");
        System.out.println(TestParseLocation.class.getResource("/").getPath()); // package所在的父目录
        File file = new File(TestParseLocation.class.getResource("/test.properties").getPath());
        try {
            inputStream = new FileInputStream(file);
            prop.load(inputStream);
            inputStream.close();
            System.out.println(prop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
