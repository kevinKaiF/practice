package y2017.m04.d21;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/21
 */
public class TestProtocJavaEntity {
    @Test
    public void testProtoc() {
        // getDefaultInstance获取默认的实例
        PersonBean.Person person = PersonBean.Person.getDefaultInstance();
        System.out.println(person.getName());
        System.out.println(person.getAge());

        // 使用builder模式构建实例
        PersonBean.Person.Builder builder = PersonBean.Person.newBuilder();
        PersonBean.Person person1 = builder.addAllTestNum(Arrays.asList(Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12)))
                .setAge(20)
                .setName("lily")
                .build();
        System.out.println(person1.toString());

    }
}
