package y2017.m07.d11;

import org.junit.Test;
import y2016.m08.day20160825.Closer;
import y2017.m07.d05.User;

import java.io.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/11
 */
public class TestSerialize {
    @Test
    public void testSerialize() {
        y2017.m07.d05.User user = new User();
        user.setName("test");
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            String fileName = "user.dat";
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            Object o = objectInputStream.readObject();
            System.out.println(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(objectOutputStream);
            Closer.closeQuietly(objectInputStream);
        }
    }
}
