package y2016.m12.d29;


/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/29
 */
public class IntMap {
    private static final int DEFAULT_SIZE = 16;
    private Object NULL_OBJECT = new Object();
    private double factor = 0.75;
    private int[] valueStore;
    private Object[] keyStore; // 记录所有的key值，keyStore与valueStore大小相同
    private int maxSize;
    private int size;

    public IntMap() {
        maxSize = DEFAULT_SIZE;
        valueStore = new int[maxSize];
        keyStore = new Object[maxSize];
        size = 0;
    }

    public int size() {
        return size;
    }

    public int get(Object key) {
        key = valueOf(key);
        int hash = hash(key);
        int hashIndex = hash % maxSize;
        while (true) {
            Object oldKey = keyStore[hashIndex];
            if (oldKey == null) {
                return -1;
            } else if (oldKey == key || oldKey.equals(key)) {
                int oldValue = valueStore[hashIndex];
                return oldValue;
            } else {
                hashIndex = (hashIndex + 1) % maxSize;
            }
        }
    }

    public int put(Object key, int value) {
        key = valueOf(key);
        int hash = hash(key);
        int hashIndex = hash % maxSize;
        while (true) {
            Object oldKey = keyStore[hashIndex];
            if (oldKey == null) {
                size++;
                keyStore[hashIndex] = key;
                valueStore[hashIndex] = value;
                if (size > maxSize * factor) {
                    resize();
                }
                return -1;
            } else if (!oldKey.equals(key) && oldKey != key) { // collision
                hashIndex = (hashIndex + 1) % maxSize;
                continue;
            } else {
                int oldValue = valueStore[hashIndex];
                valueStore[hashIndex] = value;
                return oldValue;
            }
        }
    }

    private void resize() {
        Object[] oldKeyStore = this.keyStore;
        int[] oldValueStore = this.valueStore;
        int oldMaxSize = maxSize;
        this.maxSize = this.maxSize * 2;
        this.keyStore = new Object[maxSize];
        this.valueStore = new int[maxSize];
        for (Object oldKey : oldKeyStore) {
            int oldHashIndex = hash(oldKey) % oldMaxSize;
            int oldValue = oldValueStore[oldHashIndex];

            int newHashIndex = hash(oldKey) % maxSize;
            keyStore[newHashIndex] = oldKey;
            valueStore[newHashIndex] = oldValue;
        }

    }

    public void clear() {
        for (int len = keyStore.length - 1; len >= 0; len--) {
            keyStore[len] = null;
            valueStore[len] = 0;
        }

        size = 0;
    }

    private Object valueOf(Object key) {
        return key == null ? NULL_OBJECT : key;
    }

    private int hash(Object key) {
        return System.identityHashCode(key);
    }

    public int remove(Object key) {
        key = valueOf(key);
        int hash = hash(key);
        int hashIndex = hash % maxSize;
        while (true) {
            Object oldKey = keyStore[hashIndex];
            if (oldKey == null) {
                return -1;
            } else if (oldKey == key || oldKey.equals(key)) {
                int oldValue = valueStore[hashIndex];
                keyStore[hashIndex] = null;
                valueStore[hashIndex] = 0;
                size--;
                return oldValue;
            } else {
                hashIndex = (hashIndex + 1) % maxSize;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[IntMap ");
        for (int index = keyStore.length - 1; index >= 0; index--) {
            if (keyStore[index] != null) {
                sb.append("(")
                        .append(keyStore[index])
                        .append(":")
                        .append(valueStore[index])
                        .append("), ");
            }
        }

        if (size > 0) {
            sb.deleteCharAt(sb.length() - 2);
        }

        sb.append("]");
        return sb.toString();
    }
}
