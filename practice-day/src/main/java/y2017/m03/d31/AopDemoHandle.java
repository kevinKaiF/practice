package y2017.m03.d31;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/31
 */
public class AopDemoHandle implements InvocationHandler {
    private AopDemo aopDemo = new AopDemo();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object invoke = null;
        try {
            beforeExecuteMethod(method);
            invoke = method.invoke(aopDemo, args);
            afterExecuteMethod(method);
        } catch (Exception e) {
            executeMethodCaught(e);
        }
        return invoke;
    }

    private void executeMethodCaught(Exception e) {
        System.out.println("execute method happened exception");
    }

    private void afterExecuteMethod(Method method) {
        System.out.println("after execute " + method.getName());
    }

    private void beforeExecuteMethod(Method method) {
        System.out.println("before execute " + method.getName());
    }
}
