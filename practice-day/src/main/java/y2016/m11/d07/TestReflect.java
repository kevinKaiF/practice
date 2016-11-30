package y2016.m11.d07;

import org.junit.Test;
import y2016.m09.d14.Person;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;

/**
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/7
 */
public class TestReflect {
    @Test
    public void testReflect() throws ClassNotFoundException {
        Class clazz = Class.forName(Person.class.getName());
        Method[] methods = clazz.getDeclaredMethods();
        java.util.Arrays.sort(methods, new java.util.Comparator<java.lang.reflect.Method>() {
            @Override
            public int compare(java.lang.reflect.Method o1, java.lang.reflect.Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Random random = new Random();
        String simpleName = Introspector.decapitalize(Person.class.getSimpleName());
        int nextInt = random.nextInt(100);
        String result = "";
        for (Method method : methods) {
            if (method.getName().indexOf("set") > -1) {
                Class parameterType = method.getParameterTypes()[0];
                if (parameterType.isPrimitive()) {
                    if (parameterType.isAssignableFrom(Integer.TYPE)) {
                        result += simpleName + "." + method.getName() + "(" + nextInt + ");\n";
                    } else if (parameterType.isAssignableFrom(Long.TYPE)) {
                        result += simpleName + "." + method.getName() + "(" + nextInt + "L);\n";
                    } else if (parameterType.isAssignableFrom(Boolean.TYPE)) {
                        result += simpleName + "." + method.getName() + "(" + (nextInt % 2 == 0 ? "true" : "false") + ");\n";
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else if (parameterType.isAssignableFrom(Long.class)) {
                    result += simpleName + "." + method.getName() + "(" + nextInt + "L);\n";
                } else if (parameterType.isAssignableFrom(String.class)) {
                    result += simpleName + "." + method.getName() + "(\"TEST " + method.getName().substring(3) + "\");\n";
                } else if (parameterType.isAssignableFrom(Date.class)) {
                    result += simpleName + "." + method.getName() + "(new Date());\n";
                }
            }
        }


        System.out.println(result);
    }
}
