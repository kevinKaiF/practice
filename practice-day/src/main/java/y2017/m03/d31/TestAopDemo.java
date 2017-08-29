package y2017.m03.d31;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/31
 */
public class TestAopDemo {
    @Test
    public void testAopDemo1() {
        AopDemo aopDemo = new AopDemo();
        aopDemo.eat();
        aopDemo.work();
        aopDemo.sleep();
    }

    @Test
    public void testAopDemo2() {
        IAopDemo aopDemo = (IAopDemo) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IAopDemo.class}, new AopDemoHandle());
        aopDemo.eat();
        aopDemo.work();
        aopDemo.sleep();
    }
}
