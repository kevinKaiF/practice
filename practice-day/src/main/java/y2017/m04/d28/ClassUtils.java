package y2017.m04.d28;

import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/29
 */
public abstract class ClassUtils {

    /**
     * Suffix for array class name "[]"
     */
    public static final String ARRAY_SUFFIX = "[]";

    private static final String INTERNAL_ARRAY_PREFIX = "[";

    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    private static final char PACKAGE_SEPARATOR = '.';

    private static final char PATH_SEPARATOR = '/';

    private static final char INNER_CLASS_SEPARATOR = '$';

    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    public static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * eg. Integer.class -> int.class
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<>(8);

    /**
     * eg. int.class -> Integer.class
     */
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<>(8);

    /**
     * eg. int -> int.class
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);

    private static final Map<String, Class<?>> commonClassCache = new HashMap<>(32);

    static {
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);

        for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperTypeMap.entrySet()) {
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
            registerCommonClasses(entry.getKey());
        }

        // 所有的原始类型
        Set<Class<?>> primitiveTypes = new HashSet<>(32);
        primitiveTypes.addAll(primitiveWrapperTypeMap.values());
        primitiveTypes.addAll(Arrays.asList(int[].class, long[].class, boolean[].class, double[].class,
                float[].class, char[].class, short[].class, byte[].class));
        primitiveTypes.add(void.class);

        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }

        registerCommonClasses(Integer[].class, Long[].class, Boolean[].class, Double[].class,
                Character[].class, Short[].class, Byte[].class, Float[].class);
        registerCommonClasses(Number.class, Number[].class, String.class, String[].class,
                Object.class, Object[].class, Class.class, Class[].class);
        registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class,
                Error.class, StackTraceElement.class, StackTraceElement[].class);
    }

    private static void registerCommonClasses(Class<?>... commonClasses) {
        for (Class<?> commonClass : commonClasses) {
            commonClassCache.put(commonClass.getName(), commonClass);
        }
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
            // Cannot access thread context classloader
        }
        if (classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Throwable e) {
                    // Cannot access system classloader
                }
            }
        }
        return classLoader;
    }

    public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
        Thread currentThread = Thread.currentThread();
        ClassLoader contextClassLoader = currentThread.getContextClassLoader();
        if (contextClassLoader != null && !contextClassLoader.equals(classLoaderToUse)) {
            currentThread.setContextClassLoader(classLoaderToUse);
            return contextClassLoader;
        } else {
            return null;
        }
    }

    public static Class<?> forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
        Assert.notNull(className, "className must not be null");
        Class<?> clazz = resolvePrimitiveClassName(className);
        if (clazz == null) {
            clazz = commonClassCache.get(className);
        }

        if (clazz != null) {
            return clazz;
        }

        // eg. java.lang.String[] style arrays
        if (className.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = className.substring(0, className.length() - ARRAY_SUFFIX.length());
            // 递归获取元素类型
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // eg. [Ljava.lang.String; style arrays
        if (className.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && className.endsWith(";")) {
            String elementName = className.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), className.length() - 1);
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        // [[I or [[java.lang.String;
        if (className.startsWith(INTERNAL_ARRAY_PREFIX)) {
            String elementName = className.substring(INTERNAL_ARRAY_PREFIX.length());
            Class<?> elementClass = forName(elementName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = getDefaultClassLoader();
        }

        try {
            return (classLoaderToUse != null) ? classLoaderToUse.loadClass(className) : Class.forName(className);
        } catch (ClassNotFoundException ex) {
            int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
            if (lastDotIndex != -1) {
                String innerClassName = className.substring(0, lastDotIndex) + INNER_CLASS_SEPARATOR + className.substring(lastDotIndex);
                try {
                    return (classLoaderToUse != null ? classLoaderToUse.loadClass(innerClassName) : Class.forName(innerClassName));
                } catch (ClassNotFoundException e) {
                }
            }
            throw ex;
        }
    }

    /**
     * 运行期异常可以不用声明throws，那么声明throws的意义是让应用程序处理这种可能出现的情况
     *
     * @param className
     * @param classLoader
     * @return
     * @throws IllegalArgumentException
     */
    public static Class<?> resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException{
        try {
            Class<?> clazz = forName(className, classLoader);
            return clazz;
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException("Cannot find class [" + className + "]", ex);
        } catch (LinkageError ex) {
            throw new IllegalArgumentException("Error loading class [" + className + "] problem with class file or dependent class.", ex);
        }
    }

    private static Class<?> resolvePrimitiveClassName(String name) {
        if (name != null && name.length() < 8) {
            return primitiveTypeNameMap.get(name);
        } else {
            return null;
        }
    }

    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            forName(className, classLoader);
            return true;
        } catch (Throwable e) {
            // Class or one of its dependencies is not present
            return false;
        }

    }

    public static void main(String[] args) {
        ClassUtils classUtils = new ClassUtils() {

        };
    }


}
