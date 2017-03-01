package cn.bella.server;

import cn.bella.service.HelloService;
import cn.bella.service.impl.HelloServiceImpl;

import java.io.IOException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM10:38
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8080);
        reactor.registerService(HelloService.class, HelloServiceImpl.class);
        reactor.run();

    }
}
