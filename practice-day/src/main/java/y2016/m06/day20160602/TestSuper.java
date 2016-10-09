package y2016.m06.day20160602;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-02 PM02:10
 */
public class TestSuper {
    class Father {
        public void sayAll(String... names) {
            String[] nameArr = names;
            for(String name : nameArr) {
                System.out.println(this.getClass().getSimpleName());
                say(name);
            }
        }

        public void say(String name) {
            System.out.println("father :" + name);
        }
    }

    class Child  extends Father{

        @Override
        public void sayAll(String... names) {
            // 这里尽管调用父类的方法，但是父类方法中，this是当前对象，所以一旦所调用的方法被覆盖了，会调用this中的覆盖方法
            super.sayAll(names);
        }

        @Override
        public void say(String name) {
            System.out.println("child :" + name);
        }
    }

    @Test
    public void testOverride() {
        Child child = new Child();
        child.sayAll("name1", "name2");
    }

    @Test
    // Comparator只能在Arrays.sort方法中使用！！！
    // 而Comparable在类层面使用
    public void testCharset() {
        System.out.println("中".getBytes(Charset.forName("GBK")));
        Arrays.sort(new String[]{}, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Test
    // 数组是有缺陷的
    // ArrayStoreException
    public void testArray() {
        Object[] objects = new Long[10];
        objects[0] = "sss";
        System.out.println(objects.length);

    }
}
