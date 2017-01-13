package y2016.m11.d23;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/23
 */
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundedBuffer that = (BoundedBuffer) o;

        if (putptr != that.putptr) return false;
        if (takeptr != that.takeptr) return false;
        if (count != that.count) return false;
//        if (lock != null ? !lock.equals(that.lock) : that.lock != null) return false;
//        if (notFull != null ? !notFull.equals(that.notFull) : that.notFull != null) return false;
//        if (notEmpty != null ? !notEmpty.equals(that.notEmpty) : that.notEmpty != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(items, that.items);

    }

    @Override
    public int hashCode() {
        int result = lock != null ? lock.hashCode() : 0;
        result = 31 * result + (notFull != null ? notFull.hashCode() : 0);
        result = 31 * result + (notEmpty != null ? notEmpty.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(items);
        result = 31 * result + putptr;
        result = 31 * result + takeptr;
        result = 31 * result + count;
        return result;
    }
}
