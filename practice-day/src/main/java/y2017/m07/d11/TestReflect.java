package y2017.m07.d11;

import org.junit.Test;
import org.springframework.cglib.core.ReflectUtils;
import y2016.m09.d14.Person;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/11
 */
public class TestReflect {
    @Test
    public void testReflect() {
        Map<Class, Class> primitiveToWrapperMap = new HashMap<Class, Class>() {
            {
                put(boolean.class, Boolean.class);
                put(int.class, Integer.class);
                put(long.class, Long.class);
                put(float.class, Float.class);
                put(double.class, Double.class);
                put(short.class, Short.class);
                put(byte.class, Byte.class);
                put(char.class, Character.class);
            }
        };
        Person person = (Person) ReflectUtils.newInstance(Person.class);
        Field[] declaredFields = Person.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if ("ok".equals(declaredField.getName())) {
                try {
                    declaredField.setAccessible(true);
                    Class<?> type = declaredField.getType();
                    if (type.isPrimitive()) {
                        type = primitiveToWrapperMap.get(type);
                    }
                    Object param = 1;
                    System.out.println();
//                    if (!type.isAssignableFrom(param.getClass())) {
//                        param = Boolean.class.cast(param);
//                    }
                    declaredField.set(person, Boolean.valueOf(param.toString()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    declaredField.setAccessible(false);
                }
            }
        }
//        PropertyDescriptor[] beanSetters = ReflectUtils.getBeanSetters(person.getClass());
//        for (PropertyDescriptor beanSetter : beanSetters) {
//            String name = beanSetter.getName();
//            System.out.println(name);
//            System.out.println(beanSetter.getWriteMethod().getName());
//            if ("age".equals(name)) {
//                try {
//                    beanSetter.getWriteMethod().invoke(person, 3);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
////            .invoke(person, )
//        }
        System.out.println(person);
    }
}
