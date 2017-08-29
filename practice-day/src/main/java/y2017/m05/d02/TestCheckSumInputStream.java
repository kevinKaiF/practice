package y2017.m05.d02;

import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/2
 */
public class TestCheckSumInputStream {
    @Test
    public void testCheckedOutputStreamReadBytes() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j.properties");
//        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new Adler32());
        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new ChecksumImp());

        try {
            byte[] bytes = new byte[1024 * 4];
            int read = -1;
            while ((read = checkedInputStream.read(bytes)) != -1) {
                System.out.println(read);
            }
            long value = checkedInputStream.getChecksum().getValue();
            System.out.println(value); // 3693505143
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(inputStream);
            Closer.closeQuietly(checkedInputStream);
        }
    }

    @Test
    public void testCheckedOutputStreamReadInt() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j.properties");
        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new Adler32());
//        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new ChecksumImp());

        try {
            checkedInputStream.read();
            long value = checkedInputStream.getChecksum().getValue();
            System.out.println(value); // 7143533
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(inputStream);
            Closer.closeQuietly(checkedInputStream);
        }
    }

    @Test
    public void testGetMax() throws InterruptedException {
        int maxN = 1;
        long leftExpression = getLeftExpression(maxN);
        long rightExpression = 2 * (1L << 32) - 2; // 2147483647 4296171735
        System.out.println(rightExpression);
        while (leftExpression <= rightExpression) {
            maxN++;
            leftExpression = getLeftExpression(maxN);
            System.out.println(maxN + ", lefExpress :" + leftExpression + ", rightExpression : " + rightExpression + ", result :" + (leftExpression <= rightExpression));
            Thread.sleep(5);
        }

        System.out.println(maxN); // 5553
    }

    private long getLeftExpression(int maxN) {
        return (255L * (maxN + 1) * maxN) + (maxN + 1) * 65520L * 2;
    }

    @Test
    public void testNumber() {
        System.out.println(Long.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println((1L << 32) - 1);
        System.out.println(Long.toBinaryString((1L << 32) - 1));
        System.out.println(Long.toBinaryString(1L << 32 - 1));
    }
}
