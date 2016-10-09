package y2016.m09.d14.rmi;

import java.rmi.Remote;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-14 16:29
 */
public interface HelloService extends Remote {
    void sayHello();
}
