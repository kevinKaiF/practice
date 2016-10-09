package y2016.m06.day20160601;

import org.junit.Test;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-01 PM01:24
 */
public class TestPython {
    @Test
    public void testPython() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("C:\\Users\\Administrator\\Desktop\\constant\\add.py");
        PyFunction func = interpreter.get("adder",PyFunction.class);

        int a = 2010, b = 2 ;
        PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("anwser = " + pyobj.toString());
    }

    @Test
    /**
     * 缩进必须符合Python规范
     */
    public void testPython2() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("def adder(a, b):\n" +
                "   return a + b");
        PyFunction func = interpreter.get("adder", PyFunction.class);

        int a = 2010, b = 2 ;
        List<Person> list = new ArrayList<Person>(){
            {
                add(new Person("test", 22));
                add(new Person("test", 22));
            }
        };

        PyList pyList = new PyList(list);
        System.out.println(pyList.size());
        System.out.println(pyList.get(0).toString());

        PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("anwser = " + pyobj.toString());
    }

    class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

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
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
