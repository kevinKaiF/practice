package cn.bn.main;

import cn.bn.controller.DataController;
import cn.bn.entity.DataBean;
import cn.bn.entity.TestBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM04:24
 */
public class launcher {
    public static void main(String[] args) throws InterruptedException, IOException {
//        Semaphore semaphore = new Semaphore(0);
        // 初始化的时候，会进行一次refresh事件
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//        classPathXmlApplicationContext.start();
//        classPathXmlApplicationContext.refresh();

//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.getBeanFactory();
//        beanFactory.registerSingleton("dataBean", new DataBean("test", "source"));
        DataController bean = classPathXmlApplicationContext.getBean(DataController.class);
        System.out.println(bean);

//        Properties properties = PropertiesLoaderUtils.loadAllProperties("test.properties", Thread.currentThread().getClass().getClassLoader());

//        Resource resource = new FileSystemResource("./test.properties");
//        Resource resource = new ClassPathResource("test.properties");
//        System.out.println(resource.getURI().getPath());
//        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
//
//        System.out.println(properties.getProperty("name"));

        TestBean testBean = classPathXmlApplicationContext.getBean(TestBean.class);
        System.out.println(testBean.toString());

        Thread.sleep(2000);
//        classPathXmlApplicationContext.refresh(); // 清空所有注册的bean
        classPathXmlApplicationContext.close();
//        semaphore.acquire();
    }
}
