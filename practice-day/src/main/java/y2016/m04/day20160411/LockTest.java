package y2016.m04.day20160411;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-06 AM09:15
 */
public class LockTest {

    private final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
    private final ReentrantLock lock = new ReentrantLock();
//    @Test
    public void testLockInterruptibly() {
        final LockTest test = new LockTest();
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//        Callable<BlockingQueue<String>> call1 = new Callable<BlockingQueue<String>>() {
//            @Override
//            public BlockingQueue<String> call(){
//                try {
//                    test.add("1");
//                } catch (InterruptedException e) {
//                    System.out.println("call1被中断");
//                    e.printStackTrace();
//                }
//                return test.blockingQueue;
//            }
//        };
//
//        Callable<BlockingQueue<String>> call2 = new Callable<BlockingQueue<String>>() {
//            @Override
//            public BlockingQueue<String> call(){
//                try {
//                    test.add("2");
//                } catch (InterruptedException e) {
//                    System.out.println("call2被中断");
//                    e.printStackTrace();
//                }
//                return test.blockingQueue;
//            }
//        };
//
//        Future<BlockingQueue<String>> future1 = executorService.submit(call1);
//        Future<BlockingQueue<String>> future2 = executorService.submit(call2);
//        try {
////            future1.get();
//            future1.cancel(false);
//            future2.get();
//
//            System.out.println(test.blockingQueue.size());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testLockCondition() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition();
        long startTime = System.currentTimeMillis();
//        lock.lock();
        synchronized (this) {
            try {
                int i = 10;
                System.out.println(this);
                this.wait(0);
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        finally {
//            lock.unlock();
//        }
    }

    public boolean add(String element) throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
//        lock.lock();
        try {
            return blockingQueue.add(element);
        } finally {
            lock.unlock();
        }
    }
}
