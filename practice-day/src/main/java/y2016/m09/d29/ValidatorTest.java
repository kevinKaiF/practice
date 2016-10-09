package y2016.m09.d29;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-29 AM11:48
 */
public class ValidatorTest {
    @Test
    public void testSpringBeanUtil() {
        // Spring BeanUtil复制必须属性类型一致，而且必须有对应的get方法
        Person person = new Person();
        person.setName("person");
        person.setChildren(new ArrayList<String>() {
            {
                add("name1");
                add("name2");
            }
        });

        PersonClone personClone = new PersonClone();
        BeanUtils.copyProperties(person, personClone);

        System.out.println(personClone.toString());
    }

    @Test
    public void testApacheBeanUtils() throws InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        person.setName("person");
        person.setChildren(new ArrayList<String>() {
            {
                add("name1");
                add("name2");
            }
        });

        PersonClone personClone = new PersonClone();
        org.apache.commons.beanutils.BeanUtils.copyProperties(personClone, person);

        System.out.println(personClone.toString());
    }

    class Person {
        private String name;
        private List<String> children;

        public List<String> getChildren() {
            return children;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    class PersonClone {
        private String name;
        private List<String> children;

        public List<String> getChildren() {
            return children;
        }

        public void setChildren(List<String> children) {
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "PersonClone{" +
                    "name='" + name + '\'' +
                    ", children=" + children +
                    '}';
        }
    }
}
