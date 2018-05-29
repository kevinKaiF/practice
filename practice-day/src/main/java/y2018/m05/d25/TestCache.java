package y2018.m05.d25;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestCache {

    private static int permit = 50;

    private LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
//            .expireAfterWrite(2, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<Long, AtomicLong>() {
                @Override
                public void onRemoval(RemovalNotification<Long, AtomicLong> removalNotification) {
                    RemovalCause cause = removalNotification.getCause();
                    System.out.println(cause);
                }
            })
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long aLong) throws Exception {
                    AtomicLong atomicLong = new AtomicLong(0);
                    System.out.println("load:" + atomicLong.hashCode());
                    return atomicLong;
                }
            });

    /**
     * 程序执行在纳秒级别，所以showData会在短期内执行多次
     *
     * @throws ExecutionException
     */
    public void showData() throws ExecutionException {
        long currentSeconds = System.currentTimeMillis() / 1000;
        System.out.println(currentSeconds);
        if (counter.get(currentSeconds).incrementAndGet() > permit) {
            System.out.println("exceed access");
        }
    }

    @Test
    public void testCache() {
        int limit = 1000;
        for (int i = 0; i < limit; i++) {
            try {
                showData();
            } catch (ExecutionException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * AtomicLong没有重写hashCode，使用Object的hashCode，所以可以使用hashCode来判断是否是同一个对象
     * 注意：LoadingCache判断过期的规则是get的时候按照创建时间、当前访问时间和过期时长来判断value是否过期
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testExpire() throws ExecutionException, InterruptedException {
        long currentSeconds = System.currentTimeMillis() / 1000;
        AtomicLong value = counter.get(currentSeconds);
        System.out.println("first:" + value.hashCode());
        Thread.sleep(1000);
        value = counter.get(currentSeconds);
        System.out.println("second:" + value.hashCode());
        Thread.sleep(1000);
        value = counter.get(currentSeconds);
        // 已经过期
        System.out.println("third:" + value.hashCode());
        Thread.sleep(1000);
        value = counter.get(currentSeconds);
        System.out.println("forth:" + value.hashCode());
    }
}
