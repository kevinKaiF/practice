package y2016.m05.day20160505;

import org.junit.Test;

import java.io.*;
import java.text.MessageFormat;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-05 AM09:02
 */
public class MessageFormatTest {
    public static void main(String[] args) {
        System.out.println(MessageFormat.format("{0}必须", "name"));
    }

    @Test
    public void fileDescriptorTest() throws IOException {
        //
        FileOutputStream out = new FileOutputStream(FileDescriptor.out);
        out.write('A');
        out.close();
    }

    @Test
    public void byteTest() {
        byte b = (byte) 130;
        System.out.println(b);
        // 负数运算需要处理 -1，取反
        System.out.println(b & 0xFF);
    }

    @Test
    public void stringTest() {
        // 底层是维护一个String变量，在初始化的时候，String就固定了，每次读取就是读取String中的char
        StringReader reader;
        // 底层是维护一个StringBuffer变量，每次write就是StringBuffer进行append
        // 但是getBuffer这个方法直接返回buffer变量！！！
        StringWriter writer;
    }
}
