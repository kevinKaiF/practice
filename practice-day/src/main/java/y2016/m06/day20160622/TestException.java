package y2016.m06.day20160622;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-22 AM09:11
 */
public class TestException {
    @Test
    public void testException() {
        try {
            Object o = null;
            o.toString();
        } catch (Exception e) {
            // 不会打印出cause
            e.printStackTrace();
        }
    }

    @Test
    public void testException1() {
        try {
            Object o = null;
            o.toString();
        } catch (Exception e) {
            // 会打印出cause
            throw new RuntimeException(e);
//            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Test
    public void testException2() {
        Object o = null;
        o.toString();
    }

    @Test
    public void testThreadLocalRandom() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        random.nextInt(222);
        random.setSeed(2L);
        System.out.println();
    }

    @Test
    public void testByteBuf() {
//        ByteBuf
    }

    @Test
    public void testSearch() {
        System.out.println(searchFile("TreePicker").toString());
    }

    private List<String> searchFile(String searchStr) {
        File directory = new File("F:\\学习\\EXTJS\\ext-5.1.0\\examples");
        List<String> filePaths = new ArrayList<>();
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            Queue<File> queue = new LinkedList<>();
            queue.addAll(Arrays.asList(files));
            int i = 0;
            while (!queue.isEmpty()) {
                File file = queue.poll();
                if(file.isDirectory()) {
                    queue.addAll(Arrays.asList(file.listFiles()));
                } else if(file.getName().endsWith(".js")){
                    i++;
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String str = "";
                        while ((str = reader.readLine()) != null) {
                            if(str.contains(searchStr)) {
                                filePaths.add(file.getCanonicalPath());
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("i:" + i);
        }

        return filePaths;
    }

    @Test
    public void testArrayFill() {
        Semaphore[] semaphores = new Semaphore[4];
        Arrays.fill(semaphores, new Semaphore(0));
//        fill(semaphores, 2);
        System.out.println(semaphores[1]);
    }

    private <T> void fill(T[] arr, T val) {
        if(arr == null || arr.length == 0) {

        } else {
            for (int i = 0, len = arr.length; i < len; i++) {
                arr[i] = val;
            }
        }
    }
}
