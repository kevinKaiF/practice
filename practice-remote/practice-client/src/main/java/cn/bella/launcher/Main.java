package cn.bella.launcher;

import cn.bella.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/21
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("service-client.xml");
        HelloService helloService = (HelloService) applicationContext.getBean("remoteService");
        helloService.sayHi("kevin");
    }
}
