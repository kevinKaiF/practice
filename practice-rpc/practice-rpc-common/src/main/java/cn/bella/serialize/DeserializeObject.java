package cn.bella.serialize;

import cn.bella.utils.Closer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class DeserializeObject {
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (T) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            Closer.closeQuietly(objectInputStream);
        }
    }
}
