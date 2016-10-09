package y2016.m06.day20160603;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Arrays;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-03 AM09:32
 */
public class TestChannel {
    @Test
    public void testChannelFromFileInputStream() {
        FileInputStream inputStream;
        try {
            // FileInputStream是read_only的权限方式打开文件的，所以从流中获取的FileChannel也是read_only
            // 尽管FileChannel有write方法，但是会抛出NonWritableChannelException异常
            inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\constant\\tableColumns.txt");
            FileChannel channel = inputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // FileInputStream获取的FileChannel不能write
//            channel.write(byteBuffer);
            // truncate同样是用于write
//            channel.truncate(2L);

            channel.force(false);

            // size表示当前文件的字节数据
            System.out.println(channel.size() / 1024 + "KB");

            // position方法返回文件的位置，这个位置是字节位置
            System.out.println(channel.position());

            // 带参数的position，可以定位文件新的读取位置，从第几个字节开始读取
            System.out.println(channel.position(1));


            // read方法将File中数据读取到byteBuffer中，
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.limit()];
                // bulk 批量get，
                byteBuffer.get(bytes);
                System.out.println(Arrays.toString(bytes));
                byteBuffer.clear();
            }


            // FileInputStream关闭的时候，相关的channel也会随之关闭
            inputStream.close();
            // 如果channel已经关闭，再次从channel中读取数据会抛出ClosedChannelException异常
//            System.out.println(channel.read(byteBuffer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChannelFromFileOutputStream() {
        FileLock fileLock = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\constant\\channel.txt");
            FileChannel channel = fileOutputStream.getChannel();
            fileLock = channel.lock();


            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            byteBuffer.clear();
            for (int i = 0; i < 20; i++) {
                byteBuffer.putChar('A');
            }

            // 写出的时候必须flip，不然会一直写到limit，会出现文件空洞
            byteBuffer.flip();

            // 同样地，FileOutputStream是以write_only的权限写出文件，不能read
//            channel.read(byteBuffer);

            // truncate截取文件的字节长度，如果截取数目大于文件字节长度，文件无改变，
            // 否则，将丢弃超出的字节
            channel.truncate(10);
            channel.force(true);

            // 每次write不能确保写出多少个字节，所以需要使用while的方式
            while (byteBuffer.hasRemaining()) {
                channel.write(byteBuffer);
            }
            channel.force(false); // 将变化的数据更新到磁盘

            // 同样地，FileOutputStream关闭的时候，也会关闭FileChannel
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileLock.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testWhite() {
        System.out.println((byte)' '); // 32
    }

    @Test
    public void testSelectChannel() {
        SelectableChannel selectableChannel;
        SelectionKey selectionKey;
    }

    @Test
    public void testFileLock() {
        FileLock fileLock = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\constant\\channel.txt");
            FileChannel channel = fileOutputStream.getChannel();
            fileLock = channel.lock();

            System.out.println(fileLock.isShared()); // 是否是共享锁
            System.out.println(fileLock.position());
            System.out.println(fileLock.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileLock.release();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(args.length);
        System.out.println(TestChannel.class.getClassLoader().getResource("./").getPath());
    }
}
