package y2018.m07.d06;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.python.modules.thread.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @author kevin
 * @since 2018-07-06 13:46
 */
public class TestJson {
    @Test
    public void testJson() {
        String json = "{\n                \"servId\":\"SCELITE\",\n                \"productEnum\":\"ELITE\",\n                " +
                "\"parameterMap\":" +
                "\"{\n                \"sign\":\"NqV0d3denXgC3iKuGdWu/394E6dqiJIdjKeyYrvT3PORsjKz56le1IrutapUhJcqslJBew/W5FuzsvC+u0sv8kGBwjXs0f8wtlef+W6dkReOemcnOX3QCr0ICgR45di9k/WJ0Vfnhp9Pq1TNRrYzwV+RbNir4VwrMJUvGlVD4YCi7RhxMbUdK/gTVu3qc7ILBENgu8U0eH6WLlBvKCcLtodqguu3H+mDZ5cDuNNsPVLQExPtoodFkGX23FFcZV2uECHCc4BEqVnQ8LuEBwe+sQ15PR72vyHsmKPDBw3jcb1MukmK04lPf7a+2sQEDByY97dtJO1HLsm276phxnuScQ\\u003d\\u003d\",\n                " +
                "\"content\":\"Se/huG+l0IHBHiHWViExPqGHn2VBAl1wxNjRVT8Ur+gzPXr9E0cPG5CcrwfQMlnQPUVJ1fiunyeO7smqZXdCkB7RUqqS1wuFTasUVmQip5h4qJvsY0OwS560kb6tz1ITDim4XGQrzKpd/1mi7n/pfCVO+01jGXdxf6aQ/ThmSf50uQnX33+kSzSrElN6S2h3kAqNAgAJVYQNGKQ3qiG6Msb9gCUo3x1Hje4yT73Mz8E5jrc/CQxcfkj4J/iRqk8qzQMnlncoEDcXysVSA4vZusJ9BiYTsIbmk/xaHqtYu+gyGWbLMws7lRLei5DSWUpt7IYvTVfJwctjlzLy0Zo48X/w3mJY09nf7BW9tDuep/8SET2YFpG9AgPipauVvUG87TwBEzFdfydAnsHbN42RftQ40+kSJZmhx7G++hg3wUaWtUx+WuaZOvz8AzPiAide2XkUayCT9pUTiezbZtrslvTt3p/1VcAinFW93GeTDQ8tyVcTNhcBrJS6nwoSGbgUkwU2HZakb3g934wDFpkyz4aO+VwEnb/EvZIXGA24k4Q2uwi0ShE07bw88UHlWFbi37XLrmFo8rnmWnL5+7FEu9/oFUx1cK2rqciZScFNw5itNg+Gqv1ukmH7qprUhL9x1Av8I8ou5Gjq3u0DpskYfdJaz0nMcNkC/D3oImY7AfA\\u003d\"\n                " +
                "}\"\n                " +
                "}";
        Object parse = JSON.parse(json);
        System.out.println(parse);
    }

    @Test
    public void testToJson(){
        String str = "12313";
        System.out.println(JSON.toJSONString(str));
    }

    @Test
    public void testArray() {
        int[] arr = new int[10];
        System.out.println(arr[5]);
    }

    @Test
    public void testSort() {
        int length = 8000;
        int loop = 20000;
        long[] count = new long[loop];
        int[] arr = new int[length];
        Random random = new Random();
        for (int j = 0; j < loop; j++) {
            for (int i = 0; i < length; i++) {
                arr[i] = random.nextInt(length);
            }

//        System.out.println(Arrays.toString(arr));
            long start = System.currentTimeMillis();
            Arrays.sort(arr);
            long end = System.currentTimeMillis();
//            System.out.println(end - start);
            count[j] = end - start;
        }

        Arrays.sort(count);
        System.out.println(Arrays.toString(count));
        System.out.println(count[count.length * 90 / 100]);
        System.out.println(count[count.length * 95 / 100]);
        System.out.println(count[count.length * 98 / 100]);
        System.out.println(count[count.length * 95 / 100]);
    }

    @Test
    public void testConcurrent() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int length = 800;
        int loop = 1000;
        int[] count = new int[length];
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < loop; i++) {
            int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int newValue = atomicInteger.getAndAdd(1);
                    count[newValue] = newValue;
                    if (newValue >= length -1) {
                        atomicInteger.set(0);
                    }
                }
            });
        }

        Thread.sleep(5000);

        System.out.println(Arrays.toString(count));
        System.out.println(atomicInteger.get());

    }

    @Test
    public void testNull() {
        List<String> stringList = new ArrayList<>();
        convert(stringList);
        System.out.println(stringList);
        stringList = null;
        System.out.println(stringList);
    }

    public void convert(List<String> list) {
        list = null;
    }


    @Test
    public void testArrayCopy() throws InterruptedException {
        int length = 1000;
        int loop = 2000;
        int[] arr = new int[length];
        for (int i = 0; i < loop; i++) {
            arr[i % length] = i + 1;
        }

        Semaphore semaphore = new Semaphore(0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loop; i++) {
                    int[] newArr = new int[length];
                    System.arraycopy(arr, 0, newArr, 0,  300);
                    System.out.println(Arrays.toString(newArr));
                }

                semaphore.release();
            }
        });
        thread.start();


        Thread.sleep(1000);
        semaphore.acquire();



    }

}
