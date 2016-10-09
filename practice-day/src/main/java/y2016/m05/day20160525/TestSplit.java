package y2016.m05.day20160525;

import org.junit.Test;
import cn.bidlink.framework.core.utils.DateUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-25 AM09:30
 */
public class TestSplit {
    @Test
    public void testSplit() {
        String str = "dc.canal_instance";
        System.out.println(str.split("\\.").length);
    }

    @Test
    public void testInteger() {
        System.out.println(new Integer(1) == 1);
        // Integer valueOf是装箱的过程 JVM会缓存-128~127的数据
        System.out.println(Integer.valueOf(127) == Integer.valueOf(127));
        System.out.println(Integer.valueOf(128) == Integer.valueOf(128));

        System.out.println(Integer.valueOf(-128) == Integer.valueOf(-128));
        System.out.println(Integer.valueOf(-129) == Integer.valueOf(-129));

        // new Integer 和 基本类型永远是相等的，编译器会自动拆箱
        // 下面两个是等价的
        System.out.println(new Integer(10000) == 10000);
        System.out.println(new Integer(10000).intValue() == 10000);
    }

    /**
     * foreach 底层是迭代器实现的，所以删除的过程中不能使用list自身删除
     */
    @Test
    public void testForEach() {
        List list = new ArrayList() {
            {
                add("1");
                add("2");
                add("3");
                add("3");
            }
        };

        for(Object o : list) {
            list.remove(o);
        }

        System.out.println(list.size());

    }

    @Test
    public void testList() {
        List<StringBuffer> list = new ArrayList<StringBuffer>() {
            {
                add(new StringBuffer("1"));
                add(new StringBuffer("2"));
                add(new StringBuffer("12"));
                add(new StringBuffer("3"));
                add(new StringBuffer("3"));
            }
        };

//        for(int i = 0; i < list.size(); i++) {
//            if("3".equals(list.get(i))) {
//                list.remove(i);
//            }
//        }

        Iterator<StringBuffer> iterator = list.iterator();
        while (iterator.hasNext()) {
            StringBuffer sb = iterator.next();
            if("3".equals(sb.toString())) {
                iterator.remove();
            } else {
                sb.append("1");
            }
        }

        System.out.println(list.toString());
    }

    @Test
    public void testStringContain() {
        System.out.println("1".contains(null));
    }

    @Test
    public void testReflect() {
        List<Person> persons = new ArrayList<Person>() {
            {
                add(new Person("kevin", 22));
                add(new Person("kevin1", 23));
                add(new Person("kevin2", 24));
            }
        };
        System.out.println(filter(persons).toString());
    }

    private<T> List<T> filter(List<T> sources) {
        List<T> list = new ArrayList<T>(sources);
        for(T t : list) {
            try {
                Field field = t.getClass().getDeclaredField("name");
                field.setAccessible(true);
                System.out.println(String.class.isAssignableFrom(field.getType()));
                setFieldValue(field, t, "200");
                field.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Test
    public void testDate() {
        parse("2016-02-11", "yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testMatch() {
        System.out.println("12".matches("1|2|3"));
        System.out.println("\\".matches("\\\\"));
    }
    public  Date parse(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;

        try {
            date = format.parse(str);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return date;
    }

    private <T> void setFieldValue(Field field, T t, String value) throws IllegalAccessException {
        Class<?> clazz = field.getType();
        if (clazz.isPrimitive()) {
            if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
                field.setInt(t, Integer.valueOf(value));
            } else if (long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)) {
                field.setLong(t, Long.valueOf(value));
            } else if (boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)) {
                field.setBoolean(t, Boolean.valueOf(value));
            } else if (char.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                field.setChar(t, Character.valueOf(value.charAt(0)));
            } else if (short.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)) {
                field.setShort(t, Short.valueOf(value));
            } else if (byte.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)) {
                field.setByte(t, Byte.valueOf(value));
            } else if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
                field.setDouble(t, Double.valueOf(value));
            } else if (float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)) {
                field.setFloat(t, Float.valueOf(value));
            }
            // 忽略Void
        } else {
            if (String.class.isAssignableFrom(clazz)) {
                field.set(t, value);
            } else if (Date.class.isAssignableFrom(clazz)) {
                Date date = DateUtils.parse(value);
                if (date != null) {
                    field.set(t, date);
                }
            }
        }
    }

    class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getAge() {
//            return age;
//        }
//
//        public void setAge(int age) {
//            this.age = age;
//        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
