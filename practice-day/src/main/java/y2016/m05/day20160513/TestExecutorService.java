package y2016.m05.day20160513;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-13 AM09:54
 */
public class TestExecutorService {
    @Test
    public void testExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        });
//        Thread.sleep(200);
        executorService.shutdownNow();
        while (!executorService.isShutdown()) {
            System.out.println("has not shutdown **************************************************");
        }
        if(executorService.isShutdown()) {
            System.out.println("has shutdown ===================================================");
        }

        if(executorService.isTerminated()) {
            System.out.println("has terminated");
        }
//        executorService.shutdownNow();
    }

    @Test
    public void test() {
        List<String> list = new ArrayList<String>(){
            {
                add("111");
                add("222");
                add("333");
            }
        };
        for (int i = 0; i < list.size(); i++) {
            list.add("222");
            System.out.println(list.get(i));
        }
    }

    @Test
    public void testSet() {
        Set<String> set = new LinkedHashSet<String>() {
            {
                add("111");
                add("222");
                add("333");
            }
        };

        for(String item : set) {
            System.out.println(item);
        }
    }

    @Test
    public void testAtom() {
        AtomicInteger atom = new AtomicInteger(0);
        System.out.println(atom.incrementAndGet());
    }
}
