package y2016.m12.d30;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class ReflectUtils {
    public static String getDesc(Method method) {
        StringBuilder sb = new StringBuilder(method.getName()).append("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            sb.append(getDesc(parameterType));
        }

        sb.append(")").append(getDesc(method.getReturnType()));
        return sb.toString();
    }

    private static String getDesc(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        while (clazz.isArray()) {
            sb.append("[");
            clazz = clazz.getComponentType();
        }

        if (clazz.isPrimitive()) {
            String name = clazz.getName();
            if ("void".equals(name)) {
                sb.append("V");
            } else if ("short".equals(name)) {
                sb.append("S");
            } else if ("byte".equals(name)) {
                sb.append("B");
            } else if ("int".equals(name)) {
                sb.append("I");
            } else if ("boolean".equals(name)) {
                sb.append("Z");
            } else if ("long".equals(name)) {
                sb.append("J");
            } else if ("char".equals(name)) {
                sb.append("C");
            } else if ("float".equals(name)) {
                sb.append("F");
            } else if ("double".equals(name)) {
                sb.append("D");
            }
        } else {
            sb.append("L");
            sb.append(clazz.getName().replace(".", "/"));
            sb.append(";");
        }
        return sb.toString();
    }

    public static String getName(Class<?> clazz) {
        if (clazz.isArray()) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append("[]");
                clazz = clazz.getComponentType();
            } while (clazz.isArray());

            return clazz.getName() + sb.toString();
        }
        return clazz.getName();
    }

    public static String getDesc(Constructor<?> c) {
        StringBuilder sb = new StringBuilder("(");
        Class<?>[] parameterTypes = c.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            sb.append(getDesc(parameterTypes[i]));
        }
        sb.append(")").append("V");
        return sb.toString();
    }
}
