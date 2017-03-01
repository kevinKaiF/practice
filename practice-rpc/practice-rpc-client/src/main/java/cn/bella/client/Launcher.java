package cn.bella.client;

import cn.bella.proxy.ProxyFactory;
import cn.bella.service.HelloService;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class Launcher {
    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.newInstance(HelloService.class);
        String name = helloService.getFullName("client");
        System.out.println(name);
    }

}
