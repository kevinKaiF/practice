package y2016.m08.day20160818;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-18 AM09:14
 */
public class TestHotKey {

    @Test
    public void test1() {
//        List list = new ArrayList();
//        for (Object o : list) {
//            Class<?> aClass = o.getClass();
//        }
        List<Person> persons = new ArrayList<>();
        Person person = new Person();
        for (int i = 0; i < 10; i++) {
            person.setName(String.valueOf(i));
            person.setAge(i);
            persons.add(person);
            System.out.println(persons);
        }
    }

    @Test
    public void testSub() {
    }

    class Person {
        String name;
        int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {

            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "name = " + name + ", age =" + age;
        }
    }

    interface A {
        void sayName(String name);

        String getName();
    }

    abstract class B implements A {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void sayName(String name) {

        }
    }

    class C extends B implements A {

    }
}
