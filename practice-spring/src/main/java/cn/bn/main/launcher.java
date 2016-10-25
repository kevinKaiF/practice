package cn.bn.main;

import cn.bn.entity.Car;
import cn.bn.entity.DataBean;
import cn.bn.entity.TestBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM04:24
 */
public class launcher {
    public static void main(String[] args) throws Exception {
//        Semaphore semaphore = new Semaphore(0);
        // 1.初始化的时候，会进行一次refresh事件
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//        classPathXmlApplicationContext.start();
//        classPathXmlApplicationContext.refresh();

//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.getBeanFactory();
//        beanFactory.registerSingleton("dataBean", new DataBean("test", "source"));
//        DataController bean = classPathXmlApplicationContext.getBean(DataController.class);
//        System.out.println(bean);

        // 2.测试aop
        DataBean dataBean = classPathXmlApplicationContext.getBean(DataBean.class);
        dataBean.setData("test");

        // 3.手动扫描文件
//        Properties properties = PropertiesLoaderUtils.loadAllProperties("test.properties", Thread.currentThread().getClass().getClassLoader());

//        Resource resource = new FileSystemResource("./test.properties");
//        Resource resource = new ClassPathResource("test.properties");
//        System.out.println(resource.getURI().getPath());
//        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
//
//        System.out.println(properties.getProperty("name"));

        // 4.配置注入属性
        TestBean testBean = classPathXmlApplicationContext.getBean(TestBean.class);
        System.out.println(testBean.toString());

        // 5.测试factoryBean
        Car car = classPathXmlApplicationContext.getBean(Car.class);
        System.out.println(car);
//        CarFactory carFactory = (CarFactory) classPathXmlApplicationContext.getBean("&car");
//        System.out.println("some car ? " + (carFactory.getObject() == car));
        Thread.sleep(2000);
//        classPathXmlApplicationContext.refresh(); // 清空所有注册的bean
        classPathXmlApplicationContext.close();
//        semaphore.acquire();
    }
}
