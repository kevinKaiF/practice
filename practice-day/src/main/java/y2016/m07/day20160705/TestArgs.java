package y2016.m07.day20160705;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-05 PM02:06
 */
public class TestArgs {
    /**
     * 使用java命令执行class文件时，传递的参数之间的空格可以是多个，
     * 比如java d2016.m07.day20160705.TestArgs yes no            sss.
     * args=[yes, no, sss]
     *
     * @param args the args
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-05 14:24:50
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
    }

    @Test
    public void testPrivilege() {
        System.out.println(ToStringBuilder.reflectionToString(System.getProperty("java.security.manager")));
        System.out.println(ToStringBuilder.reflectionToString(System.getSecurityManager()));;
    }

    @Test
    public void testPerson() throws NoSuchMethodException {
        Method method = Person.class.getMethod("test");
        System.out.println(ToStringBuilder.reflectionToString(method));
    }

    class Person {
        public void copy(String name, int type) {
            System.out.println(name + ":" + type);
        }

        public void test(){}
    }
}
