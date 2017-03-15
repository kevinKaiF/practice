package cn.bella.client;

import cn.bella.client.proxy.ProxyFactory;
import cn.bella.service.HelloService;
import cn.bella.service.QueryService;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/3
 */
public class ClientLauncher {
    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.newInstance(HelloService.class, true);
        String name = helloService.getFullName("client");
        System.out.println(name);

        HelloService helloService1 = ProxyFactory.newInstance(HelloService.class);
        String name1 = helloService1.getFullName("client");
        System.out.println(name1);

        QueryService queryService = ProxyFactory.newInstance(QueryService.class);
        String name2 = queryService.query();
        System.out.println(name2);
    }
}
