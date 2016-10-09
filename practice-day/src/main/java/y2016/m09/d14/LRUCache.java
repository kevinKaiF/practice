package y2016.m09.d14;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 PM03:15
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private static final int DEFAULT_MAX_CAPACITY = 1000;
    private volatile int maxCapacity;
    private final Lock lock;

    public LRUCache() {
        this(1000);
    }

    public LRUCache(int maxCapacity) {
        super(16, 0.75F, true);
        this.lock = new ReentrantLock();
        this.maxCapacity = maxCapacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.maxCapacity;
    }

    public boolean containsKey(Object key) {
        boolean var2;
        try {
            this.lock.lock();
            var2 = super.containsKey(key);
        } finally {
            this.lock.unlock();
        }

        return var2;
    }

    public V get(Object key) {
        Object var2;
        try {
            this.lock.lock();
            var2 = super.get(key);
        } finally {
            this.lock.unlock();
        }

        return (V) var2;
    }

    public V put(K key, V value) {
        Object var3;
        try {
            this.lock.lock();
            var3 = super.put(key, value);
        } finally {
            this.lock.unlock();
        }

        return (V) var3;
    }

    public V remove(Object key) {
        Object var2;
        try {
            this.lock.lock();
            var2 = super.remove(key);
        } finally {
            this.lock.unlock();
        }

        return (V) var2;
    }

    public int size() {
        int var1;
        try {
            this.lock.lock();
            var1 = super.size();
        } finally {
            this.lock.unlock();
        }

        return var1;
    }

    public void clear() {
        try {
            this.lock.lock();
            super.clear();
        } finally {
            this.lock.unlock();
        }

    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
