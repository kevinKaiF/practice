package y2016.m12.d30;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class Proxy {
    private static final AtomicLong PROXY_CLASS_COUNTER = new AtomicLong(0);

    private static final String PACKAGE_NAME = Proxy.class.getPackage().getName();

    public static InvocationHandler THROW_UNSUPPORTED_INVOKER = new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            throw new UnsupportedOperationException("Method " + method.getName() + "is not implemented.");
        }
    };

    private static final Map<ClassLoader, Map<String, Object>> proxyCacheMap = new WeakHashMap<>();

    private static final Object pendingGenerationMarker = new Object();

    public static Proxy getProxy(Class<?>... cls) {
        return getProxy(ClassHelper.getCallerClassLoader(Proxy.class), cls);
    }

    public static Proxy getProxy(ClassLoader classLoader, Class<?>[] cls) {
        if (cls.length > 65535) {
            throw new IllegalArgumentException("interfaces exceeded limit");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cls.length; i++) {
            String interfaceName = cls[i].getName();
            if (!cls[i].isInterface()) {
                throw new RuntimeException(interfaceName + " is not a interface.");
            }

            Class<?> tmp = null;
            try {
                tmp = Class.forName(interfaceName, false, classLoader);
            } catch (ClassNotFoundException e) {
            }

            if (tmp != cls[i]) {
                throw new IllegalArgumentException(interfaceName + " is not visible from Classloader.");
            }

            sb.append(interfaceName).append(";");
        }

        String key = sb.toString();

        Map<String, Object> cache = null;
        synchronized (proxyCacheMap) {
            cache = proxyCacheMap.get(classLoader);
            if (cache == null) {
                cache = new HashMap<>();
                proxyCacheMap.put(classLoader, cache);
            }
        }

        Proxy proxy = null;
        synchronized (cache) {
            do {
                Object value = cache.get(key);
                if (value instanceof Reference<?>) {
                    proxy = (Proxy) ((Reference) value).get();
                    if (proxy != null) {
                        return proxy;
                    }
                }

                if (value == pendingGenerationMarker) {
                    try {
                        cache.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    cache.put(key, pendingGenerationMarker);
                    break;
                }
            } while (true);
        }

        long id = PROXY_CLASS_COUNTER.getAndIncrement();
        String packageName = null;
        ClassGenerator ccp = null, ccm = null;

        try {
            ccp = ClassGenerator.newInstance(classLoader);
            Set<String> worked = new HashSet<>();
            List<Method> methods = new ArrayList<>();

            for (int i = 0; i < cls.length; i++) {
                if (!Modifier.isPublic(cls[i].getModifiers())) {
                    String cPackageName = cls[i].getPackage().getName();
                    if (cPackageName == null) {
                        packageName = cPackageName;
                    } else {
                        if (!cPackageName.equals(packageName)) {
                            throw new IllegalArgumentException("non-public interfaces come from different package.");
                        }
                    }

                    ccp.addInterface(cls[i]);
                    for (Method method : cls[i].getMethods()) {
                        String desc = ReflectUtils.getDesc(method);
                        if (!worked.contains(desc)) {
                            worked.add(desc);
                        }

                        int size = methods.size();
                        Class<?> returnType = method.getReturnType();
                        Class<?>[] parameterTypes = method.getParameterTypes();

                        StringBuilder methodBody = new StringBuilder("Object[] args = new Object[").append(parameterTypes.length).append("];");
                        for (int j = 0; j < parameterTypes.length; j++) {
                            methodBody.append("args[").append(j).append("] = ").append("($w)$").append(j + 1).append(";");
                        }
                        methodBody.append(" Object ret = handler.invoker(this, methods[" + size + "], args;");

                        if (!Void.class.equals(returnType)) {
                            methodBody.append("return ").append(asArgument(returnType, "ret")).append(";");
                        }

                        methods.add(method);
                        ccp.addMethod(method.getName(), method.getModifiers(), returnType, parameterTypes, method.getExceptionTypes(), methodBody.toString());
                    }
                }

                if (packageName == null) {
                    packageName = PACKAGE_NAME;
                }
            }

            // create proxyInstance class
            String proxyInstancePackageName = packageName + ".proxy" + id;
            ccp.setClassName(proxyInstancePackageName);
            ccp.addField("public static java.lang.Reflect.Method methods;");
            ccp.addField("private " + InvocationHandler.class.getName() + " handler");
            ccp.addConstructor(Modifier.PUBLIC, new Class<?>[]{InvocationHandler.class}, new Class<?>[0], "handler=$1");
            ccp.addDefaultConstructor();
            Class<?> clazz = ccp.toClass();
            clazz.getField("methods").set(null, methods.toArray(new Method[0]));

            // create proxy class
            String proxyPackageName = Proxy.class.getName() + id;
            ccm = ClassGenerator.newInstance(classLoader);
            ccm.setClassName(proxyPackageName);
            ccm.addDefaultConstructor();
            ccm.setSuperClass(Proxy.class);
            ccm.addMethod("public Object newInstance(" + InvocationHandler.class.getName() + " h {return new " + proxyPackageName + "($1); }");
            Class<?> proxyClass = ccm.toClass();
            proxy = (Proxy) proxyClass.newInstance();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            // release ClassGenerator
            if (ccp != null) {
                ccp.release();
            }

            if (ccm != null) {
                ccm.release();
            }

            synchronized (cache) {
                if (proxy == null) {
                    cache.remove(key);
                } else {
                    cache.put(key, new WeakReference<Proxy>(proxy));
                }

                cache.notifyAll();
            }
        }
        return proxy;
    }


    private static String asArgument(Class<?> returnType, String returnValue) {
        if (returnType.isPrimitive()) {
            if (Boolean.TYPE.equals(returnType)) {
                return returnValue + " == null ? false : ((Boolean)" + returnValue + ").toBooleanValue();";
            } else if (Byte.TYPE.equals(returnType)) {
                return returnValue + " == null ? (byte)0 : ((Byte)" + returnValue + ").toByteValue();";
            } else if (Short.TYPE.equals(returnType)) {
                return returnValue + " == null ? (short)0 : ((Short)" + returnValue + ").toShortValue();";
            } else if (Integer.TYPE.equals(returnType)) {
                return returnValue + " == null ? (int)0 : ((Integer)" + returnValue + ").toIntValue();";
            } else if (Long.TYPE.equals(returnType)) {
                return returnValue + " == null ? (long)0 : ((Long)" + returnValue + ").toLongValue();";
            } else if (Character.TYPE.equals(returnType)) {
                return returnValue + " == null ? (char)0 : ((Character)" + returnValue + ").toCharValue();";
            } else if (Float.TYPE.equals(returnType)) {
                return returnValue + " == null ? (float)0 : ((Float)" + returnValue + ").toFloatValue();";
            } else if (Double.TYPE.equals(returnType)) {
                return returnValue + " == null ? (double)0 : ((Double)" + returnValue + ").toDoubleValue();";
            }
            throw new RuntimeException(returnValue + " is a unknown primitive type.");
        }
        return "(" + ReflectUtils.getName(returnType) + ")" + returnValue;
    }

}
