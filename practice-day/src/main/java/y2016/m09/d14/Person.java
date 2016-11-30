package y2016.m09.d14;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 PM02:34
 */
public class Person {
    private String name;
    private String password;
    private int age;
    private Long roadLength;

    public Person(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(Long roadLength) {
        this.roadLength = roadLength;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return !(password != null ? !password.equals(person.password) : person.password != null);

    }

    @Override
    public int hashCode() {
       return 31;
    }
}
