package y2017.m06.d21;

import com.alibaba.fastjson.JSONObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/21
 */
public class TestGroovy {
    @Test
    public void testGroovy() {
        GroovyShell shell = new GroovyShell();
        String scriptText = "import y2017.m06.d21.TestGroovy.Person;" +
                "public List<Person> getList(List<Person> list) {\n" +
                "        Collections.shuffle(list);\n" +
                "        return list;\n" +
                "    }";
//        Script script = shell.parse("System.out.print(\"hello world\")");
        Script script = shell.parse(scriptText);
        Object run = script.invokeMethod("getList", Arrays.asList(new Person[]{
                new Person("test1"),
                new Person("test2"),
                new Person("test3"),
                new Person("test4")
        }));
        System.out.println(run);
    }

     class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testStr() {
        System.out.print("import y2017.m06.d21.TestGroovy.Person;\n\r");
        System.out.println("test");
        String jsonString = JSONObject.toJSONString("test");
        System.out.println(jsonString);
    }

    @Test
    public void testRuntime() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        System.out.println(freeMemory);
        System.out.println(maxMemory);
        System.out.println(totalMemory);
    }

    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }




}
