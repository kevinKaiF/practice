package y2016.m04.day20160425;

import java.io.File;
import java.lang.reflect.Field;

/**
 * File类的操作委托与FileSystem类
 * 在window（64位）中，
 *    FileSystem
 *         |------Win32FileSystem
 *                     |--------WinNTFileSystem
 * 委托于FileSystem是因为不同的操作系统有不同的实现
 *
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-04-25 PM01:11
 */
public class FileTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        File file = new File("F:\\学习\\ELK\\ELK.md");
        Class clazz = File.class;
        Field field = clazz.getDeclaredField("prefixLength");
        field.setAccessible(true);
        System.out.println(field.get(file));
        System.out.println((int)'/');
        // getTotalSpace获取所在磁盘总容量
        System.out.println(file.getTotalSpace());
        // getFreeSpace获取所在磁盘可用容量
        System.out.println(file.getFreeSpace());
        // length获取文件大小
        System.out.println(file.length());
    }
}
