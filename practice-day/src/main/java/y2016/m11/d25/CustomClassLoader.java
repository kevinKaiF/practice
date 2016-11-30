package y2016.m11.d25;

import org.apache.commons.lang.builder.ToStringBuilder;
import y2016.m08.day20160825.Closer;
import y2016.m11.d23.BoundedBuffer;

import java.io.*;
import java.net.URLClassLoader;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/25
 */
public class CustomClassLoader extends ClassLoader {
    private String classSuffix = ".class";

    public static void main(String[] args) {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            // loadClass采用双亲委托模式,委托父类加载,如果父类未加载,则调用findClass方法,
            Class<?> loadClass = customClassLoader.loadClass(BoundedBuffer.class.getName());
            // 子类覆写父类的方法,如果直接调用,不同命名空间加载的字节码是不同的字节码,也会生成不同的对象
//            Class<?> loadClass = customClassLoader.findClass(BoundedBuffer.class.getName());
            Object boundedBuffer = loadClass.newInstance();
            System.out.println(boundedBuffer instanceof BoundedBuffer);
            System.out.println(ToStringBuilder.reflectionToString(boundedBuffer));
            BoundedBuffer boundedBuffer1 = new BoundedBuffer();
            BoundedBuffer boundedBuffer2 = new BoundedBuffer();
            System.out.println(BoundedBuffer.class.getClassLoader() instanceof URLClassLoader);
            System.out.println(boundedBuffer.equals(boundedBuffer1));
            System.out.println(boundedBuffer2.equals(boundedBuffer1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = doLoadClass(name);
        return this.defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] doLoadClass(String name) {
        String currentClassPath = name.replace('.', File.separatorChar);
        String parentPath = "E:\\workspace\\practice\\practice-day\\target\\classes\\";
        String fullClassPath = parentPath + currentClassPath + classSuffix;
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        byte[] bytes = new byte[1024];
        try {
            out = new ByteArrayOutputStream();
            in = new FileInputStream(fullClassPath);
            int cursor = -1;
            while ((cursor = in.read(bytes)) != -1) {
                out.write(bytes, 0, cursor);
//                out.write(cursor);
            }

            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(out);
            Closer.closeQuietly(in);
        }
        return null;
    }
}
