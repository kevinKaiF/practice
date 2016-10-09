package y2016.m05.day20160503;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-03 AM09:04
 */
public class LongArray {

    @Test
    public void arrayLength() {
        // 数组的最大长度小于Integer.MAX_VALUE,具体和Jvm的实现有关系，所以通常的最大值是Integer.MAX_VALUE - 3.
        long[] longs = new long[Integer.MAX_VALUE - 1];
        System.out.println(longs.length);
        longs = null;
        System.gc();
    }

    @Test
    public void arrayCopyOfRange() {
        int[] nums = new int[10];
        int[] numsCopy = Arrays.copyOfRange(nums, 0, 11);
        /*public static int[] copyOfRange(int[] original, int from, int to) {
            int newLength = to - from;
            if(newLength < 0) {
                throw new IllegalArgumentException(from + ">" + to);
            }

            int[] newArray = new int[newLength];
            System.arraycopy(original, from, newArray, 0, Math.min(original.length - from, newLength));
            return newArray;
        }*/
        System.out.println(numsCopy.length);
    }

    @Test
    public void arrayCopy() {
        int[] nums = new int[10];
        int[] numsCopy = Arrays.copyOf(nums, 11);
        /*public static int[] copyOf(int[] original, int newLength) {
            int[] newArray = new int[newLength];
            System.arraycopy(original, 0 , newLength, 0, Math.min(original.length, newLength));
            return newArray;
        }*/
    }

    @Test
    public void runtimeTest() {
        // 底层实际是IdentityHashMap
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("yes");
            }
        });
    }

    @Before
    public void before() {
        System.out.println("threadJoinTest starting...");
    }

    @Test
    public void threadJoinTest() {
        // join就是等待当前线程到死
        Thread[] threads = new Thread[20];
        for(int i = 0; i < 20; i++) {
            final int num = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    super.run();
//                    while (true) {
                        System.out.println(Thread.currentThread().getName() + ":" + num);
                        System.out.println(Thread.currentThread().getName() + ":" + num);
                        System.out.println(Thread.currentThread().getName() + ":" + num);
                    System.out.println(Thread.currentThread().getName() + ",当前活跃线程数目：" + Thread.activeCount());
//                    }
                }
            };
        }

        System.out.println("starting...");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            threads[i].start();
//            如果在start之后直接join实际上同步阻塞的
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        这样子join是异步阻塞的，每个线程等待自己到死
        for (int i = 0; i < 20; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ending...");
        System.out.println("endTime - startTime = " + (endTime - startTime) + "ms");
    }

    @After
    public void after() {
        System.out.println("threadJoinTest ending...");
    }
}
