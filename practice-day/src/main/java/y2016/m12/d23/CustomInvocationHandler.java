package y2016.m12.d23;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/23
 */
public class CustomInvocationHandler implements InvocationHandler {
    private Object target;

    public CustomInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
