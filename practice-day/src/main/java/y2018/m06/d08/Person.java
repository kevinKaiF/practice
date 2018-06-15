package y2018.m06.d08;

/**
 * innerBuilder plugin test
 *
 * @author kevin
 * @since 2018-06-11 13:37
 */
public class Person {

    public static Builder newBuilder() {
        return new Builder();
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Person(Builder builder) {
        setName(builder.name);
        setAge(builder.age);
    }

    private String name;

    private int age;


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final class Builder {

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Person build() {
            return new Person(this);
        }

        private Builder() {
        }

        private String name;

        private int age;
    }

}
