package y2016.m09.d28;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-28 PM02:06
 */
public class Ob {
    private String name;
    public Ob(String name) {
        System.out.println("ob, name = " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
