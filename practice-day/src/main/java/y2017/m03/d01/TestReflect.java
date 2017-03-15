package y2017.m03.d01;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.concurrent.SynchronousQueue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class TestReflect {
    @Test
    public void testReflect() {
        Itest itest = (Itest) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Itest.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                method.invoke(null, args);
                System.out.println("execute");
                return null;
            }
        });
        itest.sayName("name");
    }

    interface Itest{
        void sayName(String name);
    }

    @Test
    public void testSerialize() {
        Sun sun = new Sun();

    }

    class Sun implements Serializable {
        private Method m;

        public Method getM() {
            return m;
        }

        public void setM(Method m) {
            this.m = m;
        }
    }

    @Test
    public void testQueue() throws InterruptedException {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                queue.offer("test1");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    System.out.println(queue.take());
                    System.out.println(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();


//        queue.offer("test1");
//        queue.offer("test1");
//        queue.offer("test1");
//        queue.offer("test1");
//        System.out.println(queue.size());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
        Thread.sleep(3000);
    }

    @Test
    public void testBigDecimal() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1111111.111111);
        System.out.println(bigDecimal.doubleValue());
    }
}
