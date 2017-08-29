package y2016.m05.day20160523;

import org.junit.Test;

import java.util.Date;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-23 PM03:19
 */
public class TestString {
    public static void main(String[] args) {
        String s = "123";
        System.out.println(String.format("\t", s));
    }

    /**
     * %[index$][标识][最小宽度][.精度]转换方式
     * index表示format中的第几个参数，使用时必须配合$一起使用，可以省略
     *
     * 标识
     *  '-'    在最小宽度内左对齐，不可以与“用0填充”同时使用
     *  '#'    只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
     *  '+'    结果总是包括一个符号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
     *  ' '    正值前加空格，负值前加负号（一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制）
     *  '0'    结果将用零来填充
     *  ','    只适用于10进制，每3位数字之间用“，”分隔
     *  '('    若参数是负数，则结果中不添加负号而是用圆括号把数字括起来（同‘+’具有同样的限制）
     *
     *  最小宽度    指定格式化的长度，长度不足可以用标识来填充
     *
     *  精度  只适用于浮点数，配合.使用，表示小数点后的位数
     *
     *  转换方式
     *  s   表示格式化字符串
     *  d   表示格式化十进制
     *  f   表示格式化浮点数
     *  o   表示格式化八进制
     *  x   表示格式化十六进制
     *  e E 表示格式化为用计算机科学记数法表示的十进制数
     *  g G 根据具体情况，自动选择用普通表示方式还是科学计数法方式
     *  a A 表示格式化为带有效位数和指数的十六进制浮点数
     *  t T 表示格式化为日期格式
     */
    @Test
    public void testFormatString() {
        String s1 = "test1";
        String s2 = "test2";
        System.out.println(String.format("%1$-10s %2$s\tppp", s1, s2));
        System.out.println(String.format("%-10s \n%s", s1, s2));
        System.out.println(String.format("%10s %s", s1, s2));
    }


    @Test
    public void testFormatFloat() {
        float f = 1.23F;
        System.out.println(String.format("%f", f));
        System.out.println(String.format("%+f", f));    // 结果始终包含正负符号
        System.out.println(String.format("%+f", -f));
        System.out.println(String.format("%(f", f));
        System.out.println(String.format("%(f", -f));  // (表示如果输入是负数其结果将会用（）包起来，并去掉负号
        System.out.println(String.format("%e", f));
        System.out.println(String.format("%.10f", f));
    }

    @Test
    public void testFormatInt() {
        System.out.println(String.format("%d", 0x11));
        System.out.println(String.format("%010d", 1));  // 位数不够补0
//        System.out.println(String.format("%0-10d", 1));  // -和0不能一起使用
        System.out.println(String.format("%10d", 1));
        System.out.println(String.format("%-10d", 1));
        System.out.println(String.format("%-10d2", 1));
        System.out.println(String.format("%x", 17));    // 16进制
        System.out.println(String.format("%,d", 1231231223)); // ,表示每三位分隔，只能与d配合使用
    }

    @Test
    public void testFormatDate() {
        Date date = new Date();
        System.out.println(String.format("%tY-%tm-%td", date, date, date)); // 话说有人真这么使用？？
    }

    @Test
    public void testCopy() {
        Person person = new Person("kevin", 22);
        Person copyPerson = person.clone();
        System.out.println(person.equals(copyPerson));
    }

    class Person implements Cloneable {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        protected Person clone() {
            return new Person(this.name, this.age);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (age != person.age) return false;
            return name.equals(person.name);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + age;
            return result;
        }
    }
}
