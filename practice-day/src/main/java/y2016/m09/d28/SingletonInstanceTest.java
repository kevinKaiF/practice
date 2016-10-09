package y2016.m09.d28;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-28 PM01:22
 */
public class SingletonInstanceTest {
    private static Logger logger = LoggerFactory.getLogger(SingletonInstanceTest.class);
    public static void main(String[] args) {
//        System.out.println(SingletonInstance.ob.getName());
//        SingletonInstance.sayHello();
        logger.debug("init");
        SingletonInstance singletonInstance = SingletonInstance.SingletonInstanceHolder.singletonInstance;

//        new SingletonInstance.SingletonInstanceHolder();
    }
}
