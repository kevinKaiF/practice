package y2016.m09.d14;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-14 PM02:20
 */
public class TestHashMap {
    public static void main(String[] args) {
        Map<Person, String> map = new HashMap<>();
        Person p1 = new Person("TEST1", "pwd1", 1);
        Person p2 = new Person("TEST2", "pwd2", 2);
        Person p3 = new Person("TEST3", "pwd3", 3);
        Person p4 = new Person("TEST4", "pwd4", 4);
        map.put(p1, "p1");
        map.put(p2, "p2");
        map.put(p3, "p3");
        map.put(p4, "p4");

        System.out.println(map);

        p4.setAge(44);

        map.remove(p4);
        System.out.println(map);

//        Set<Person> personSet = new HashSet<>();
//        Person p1 = new Person("TEST1", "pwd1", 1);
//        Person p2 = new Person("TEST2", "pwd2", 2);
//        Person p3 = new Person("TEST3", "pwd3", 3);
//        Person p4 = new Person("TEST4", "pwd4", 4);
//        System.out.println(p1.hashCode());
//        System.out.println(p2.hashCode());
//        System.out.println(p3.hashCode());
//        System.out.println(p4.hashCode());
//        personSet.add(p1);
//        personSet.add(p2);
//        personSet.add(p3);
//        personSet.add(p4);
//        System.out.println(personSet);
//
//        p4.setAge(44);
//        personSet.remove(p4);
//        System.out.println(personSet);
    }
}
