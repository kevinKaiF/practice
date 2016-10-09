package y2016.m09.d14.rmi;

import java.io.Serializable;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-14 PM04:43
 */
public class HelloServiceImpl implements HelloService, Serializable {

    @Override
    public void sayHello() {
        System.out.println("hello world!");
    }


}
