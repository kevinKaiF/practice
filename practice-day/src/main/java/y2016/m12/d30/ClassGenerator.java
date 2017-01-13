package y2016.m12.d30;

import javassist.ClassPool;
import javassist.LoaderClassPath;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class ClassGenerator {
    private static final AtomicLong CLASS_NAME_COUNTER = new AtomicLong(0);

    private static final String SIMPLE_NAME_TAG = "<init>";

    private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<>();

    private ClassPool mPool;
    private ClassGenerator(ClassPool classPool) {
        this.mPool = classPool;
    }

    public static ClassGenerator newInstance() {
        return new ClassGenerator(getClassPool(Thread.currentThread().getContextClassLoader()));
    }

    public static ClassGenerator newInstance(ClassLoader classLoader) {
        return new ClassGenerator(getClassPool(classLoader));
    }

    private static ClassPool getClassPool(ClassLoader classLoader) {
        if (classLoader == null) {
            return ClassPool.getDefault();
        }

        ClassPool classPool = POOL_MAP.get(classLoader);
        if (classPool == null) {
            classPool = new ClassPool(true);
            classPool.appendClassPath(new LoaderClassPath(classLoader));
            POOL_MAP.put(classLoader, classPool);
        }

        return classPool;
    }

    public void addInterface(Class<?> cl) {

    }

    public void addMethod(String name, int modifiers, Class<?> returnType, Class<?>[] parameterTypes, Class<?>[] exceptionTypes, String s) {
    }

    public void setClassName(String packageName) {

    }

    public void addField(String s) {

    }

    public void addConstructor(int modifier, Class<?>[] classes, Class<?>[] classes1, String s) {
    }

    public void addDefaultConstructor() {

    }

    public Class<?> toClass() {
        return null;
    }


    public void setSuperClass(Class<Proxy> proxyClass) {

    }

    public void addMethod(String s) {

    }

    public void release() {

    }
}
