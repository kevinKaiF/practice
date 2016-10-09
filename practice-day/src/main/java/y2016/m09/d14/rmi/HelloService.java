package y2016.m09.d14.rmi;

import java.rmi.Remote;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 16:29
 */
public interface HelloService extends Remote {
    void sayHello();
}
