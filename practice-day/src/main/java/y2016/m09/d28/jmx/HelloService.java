package y2016.m09.d28.jmx;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-28 PM04:56
 */
public class HelloService implements HelloServiceMBean {
    @Override
    public String getName() {
        return "kevin";
    }
}
