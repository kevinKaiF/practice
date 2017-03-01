package y2017.m03.d01;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
}
