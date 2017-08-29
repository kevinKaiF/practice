package y2017.m03.d31;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/31
 */
public class AopDemo implements IAopDemo{
    @Override
    public void eat() {
        System.out.println("eat");
    }

    @Override
    public void work() {
        System.out.println("work");
    }

    @Override
    public void sleep() {
        System.out.println("sleep");
        throw new RuntimeException("exception in sleep Method");
    }
}
