package com.bella.dubbo.client;

import com.bella.service.HelloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/22
 */
public class Launcher {
    private ClassPathXmlApplicationContext context;

    @Before
    public void setUp() throws FileNotFoundException {
        Log4jConfigurer.initLogging("classpath:log4j.properties", 100000);
        context = new ClassPathXmlApplicationContext("classpath:application-dubbo.xml");
    }

    @Test
    public void testHelloService() {
        HelloService helloService = (HelloService) context.getBean("helloService");
        String name = helloService.getName();
        Assert.assertEquals("3343", name);
        System.out.println(name);
    }
}
