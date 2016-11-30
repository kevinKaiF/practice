package y2016.m11.d01;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/1
 */
public class TestUrl {
    @Test
    public void testUrl() {
        try {
            URL url = new URL("kevin:www.kevin.com");
            System.out.println(url.getProtocol());
            System.out.println(url.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClassPathResource() {
        ClassPathResource classPathResource = new ClassPathResource("test.properties");
        try {
            URL url = classPathResource.getURL();
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSystemUrl() throws MalformedURLException {
        System.out.println(new File("").toURI().toString());
        System.out.println(new File("").toURI().toURL().toString());
    }
}
