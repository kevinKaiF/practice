package y2016.m06.day20160623;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-23 AM09:54
 */
public class TestStringBuilder {
    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("222222m");
        sb.delete(sb.length() - 1, sb.length());
        System.out.println(sb.toString());
    }

    @Test
    public void testSemaphore() {
        final Semaphore semaphore = new Semaphore(0);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始获取信号量");
                    semaphore.acquire();
                    System.out.println("success");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("开始释放信号量");
//                semaphore.release();
//                System.out.println("已释放信号量");
                System.out.println(semaphore.availablePermits());
            }
        });
        t1.start();
        t2.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSemaphore1() {
        Semaphore semaphore = new Semaphore(0);
        System.out.println(semaphore.availablePermits());
    }

    @Test
    public void testSemaphoreArr() throws InterruptedException {
        final Semaphore[] semaphores = new Semaphore[] {new Semaphore(0), new Semaphore(0), new Semaphore(0), new Semaphore(0)};
        final Map<String, List<String>> map = new HashMap<String, List<String>>() {
            {
                put("0", new ArrayList<String>());
                put("1", new ArrayList<String>());
                put("2", new ArrayList<String>());
                put("3", new ArrayList<String>());
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        List<String> names = map.get(String.valueOf(finalI));
                        if(CollectionUtils.isEmpty(names)) {
                            try {
                                System.out.println("thread:" + Thread.currentThread().getName());
                                semaphores[finalI].acquire();
                                System.out.println("thread:" + Thread.currentThread().getName() + ", i:" + finalI);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(names);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int random = ThreadLocalRandom.current().nextInt(4);
                List<String> list = map.get(String.valueOf(random));
                String temp = random + ":" + System.currentTimeMillis();
                list.add(temp);
                if(semaphores[random].availablePermits() == 0) {
                    System.out.println(temp);
                    semaphores[random].release();
                }
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);

        Thread.currentThread().join();
        Thread.interrupted();
    }
}
