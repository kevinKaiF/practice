package y2016.m09.d28.jmx;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-28 PM04:56
 */
public class HelloService implements HelloServiceMBean {
    @Override
    public String getName() {
        return "kevin";
    }
}
