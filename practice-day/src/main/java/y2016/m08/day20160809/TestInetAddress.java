package y2016.m08.day20160809;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-09 PM01:48
 */
public class TestInetAddress {
    @Test
    public void testInetAddress() throws UnknownHostException {
        System.out.println(ToStringBuilder.reflectionToString(InetAddress.getByName(null)));;
    }

    @Test
    public void testFile() {
//        File file = new File("C:\\Users\\Administrator\\Desktop\\工作总结.txt");
//        // 从相对路径中获取对应的绝对路径文件
//        File abFile = file.getAbsoluteFile();
//        System.out.println(abFile == file);
//        System.out.println(abFile.equals(file));
//        System.out.println(abFile.getPath());
//        // 文件重命名
//        file.renameTo(new File("C:\\Users\\Administrator\\Desktop\\工作总结1.txt"));
        Object[] objects = new Object[]{"1"};
        System.out.println(Arrays.toString(objects));
    }

    public class ArrayQueue<T> {
        private Object[] arr;
        private int maxSize;
        private int index;
        private static final int DEFAULT_CAPACITY = 8;

        public ArrayQueue() {
            this(DEFAULT_CAPACITY);
            System.out.println("实例化");
        }

        public ArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            this.arr = new Object[maxSize];
            this.index = 0;
        }

        public boolean add(T t) {
            arr[index++] = t;
            return true;
        }

        public T get(int index) {
            return (T) arr[index];
        }
    }

}
