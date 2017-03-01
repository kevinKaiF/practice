package cn.bella.serialize;

import cn.bella.utils.Closer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class SerializeObject {
    public static byte[] serialize(Object obj) {
        ObjectOutputStream objectOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Closer.closeQuietly(objectOutputStream);
        }
    }
}
