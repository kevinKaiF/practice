package y2016.m06.day20160601;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 异步查询同步结果
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-01 AM11:29
 */
public class AsyncResult {
    private AtomicBoolean doGet = new AtomicBoolean(false);
    private byte[] result;
    private Lock lock = new ReentrantLock();
    private Condition condition;
    private long startTime;

    public AsyncResult() {
        this.startTime = System.currentTimeMillis();
        this.condition = lock.newCondition();
    }

    public byte[] get() {
        lock.lock();
        try {
            if(!doGet.get()) {
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public byte[] get(long timeout, TimeUnit timeUnit) throws TimeoutException {
        lock.lock();
        try {
            boolean bVal = false;
            if(!doGet.get()) { // 如果还未获取到数据，等待获取
                long nanos = timeUnit.toMillis(timeout);
                long overTime = nanos - (System.nanoTime() - startTime);
                if(overTime > 0) {
                    // await返回false，表示在waiting的这段时间里并没有被signal，否则true
                    bVal = condition.await(overTime, TimeUnit.MILLISECONDS);
                } else {
                    bVal = false;
                }
            }

            if(!bVal && !doGet.get()) {
                throw new TimeoutException("operation timeout!");
            }
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void result() {
        lock.lock();
        try {
            if(!doGet.get()) {
                result = getResponse();
                doGet.set(true);
                condition.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public byte[] getResponse() {
        return new byte[]{};
    }
}
