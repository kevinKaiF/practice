package y2016.m12.d23;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/23
 */
public class TestProxy {
    @Test
    public void testJdkProxy() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        final HelloService helloService = new HelloServiceImpl();
        HelloService proxyInstance = (HelloService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{HelloService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(helloService, args);
            }
        });

        String name = proxyInstance.getName();
        System.out.println(name);
    }

    @Test
    public void testModifier() {
        System.out.println(Modifier.isPublic(HelloService.class.getModifiers()));
    }

    /**
     * 生成Proxy代理类，使用sun.misc.ProxyGenerator.saveGeneratedFiles参数来设置保存代理类。
     * 如果代理接口的public的则会生成在项目的com/sun/proxy目录下（相对于项目根目录）
     * 否则在代理对应的目录下（相对于项目根目录）
     * @param args
     */
    public static void main(String[] args) {
//        需要在工程根目录建立com/sun/proxy文件夹
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        final HelloService helloService = new HelloServiceImpl();
        HelloService proxyInstance = (HelloService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{HelloService.class},
                new CustomInvocationHandler(helloService));

        String name = proxyInstance.getName();
        System.out.println(name);

    }

    @Test
    public void testCGLib() {
        HelloService helloService = CGLibFactory.getInstance().createProxy(HelloServiceImpl.class);
        System.out.println(helloService.getName());
    }
}
