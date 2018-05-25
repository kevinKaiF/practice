package y2018.m05;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import groovy.lang.DelegatesTo;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestCache {

    private static int permit = 50;

    private LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long aLong) throws Exception {
                    return new AtomicLong(0);
                }
            });

    public void showData() throws ExecutionException {
        long currentSeconds = System.currentTimeMillis() / 1000;
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

}
