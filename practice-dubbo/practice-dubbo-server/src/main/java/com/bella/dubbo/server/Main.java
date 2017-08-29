package com.bella.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/22
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        Semaphore semaphore = new Semaphore(0);
        Log4jConfigurer.initLogging("classpath:log4j.properties", 100000);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-dubbo.xml");
//        context.refresh();
//        context.start();
        semaphore.acquire();
    }
}
