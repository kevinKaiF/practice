package y2017.m10.d25;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/10/25
 */
public class TestString {
    ThreadLocal<Date> currentDate = new InheritableThreadLocal<>();
    @Test
    public void testString() {
        String countUpdatedSql = "SELECT count(1) FROM\n"
                                 + "   bmpfjz_project bp\n"
                                 + "JOIN bmpfjz_project_ext bpe ON bp.id = bpe.id\n"
                                 + "WHERE\n"
                                 + "   bpe.bid_result_show_type = 1\n"
                                 + "AND bp.update_time > ?\n"
                                 + "AND bp.project_status IN (5, 6, 10)";
        System.out.println(countUpdatedSql);
    }

    @Test
    public void testThreads() throws InterruptedException {
        currentDate.set(new Date());

        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Date date = currentDate.get();
                    System.out.println(date);
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        System.out.println("over");
    }
}
