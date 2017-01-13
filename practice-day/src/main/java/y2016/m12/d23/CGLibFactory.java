package y2016.m12.d23;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/23
 */
public class CGLibFactory {
    private CGLibFactory(){};

    private static CGLibFactory cgLibFactory = new CGLibFactory();
    public static CGLibFactory getInstance() {
        return cgLibFactory;
    }

    public <T> T createProxy(Class<T> target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(new ProxyMethodInterceptor());
        return (T) enhancer.create();
    }

    private class ProxyMethodInterceptor<T> implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
        }
    }


}
