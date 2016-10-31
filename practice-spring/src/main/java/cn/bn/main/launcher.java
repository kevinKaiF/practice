package cn.bn.main;

import cn.bn.entity.Car;
import cn.bn.entity.DataBean;
import cn.bn.entity.TestBean;
import cn.bn.service.DataBeanService;
import cn.bn.service.TestBeanService;
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
//        TestBean testBean = classPathXmlApplicationContext.getBean(TestBean.class);
//        System.out.println(testBean.toString());

        // 5.测试factoryBean
        Car car = classPathXmlApplicationContext.getBean(Car.class);
        System.out.println(car);
//        CarFactory carFactory = (CarFactory) classPathXmlApplicationContext.getBean("&car");
//        System.out.println("some car ? " + (carFactory.getObject() == car));

        // 6.接口注入
        DataBeanService dataBeanService = classPathXmlApplicationContext.getBean(DataBeanService.class);
        TestBeanService testBeanService = classPathXmlApplicationContext.getBean(TestBeanService.class);

        // 7.自定义属性编辑器
        // 自定义属性编辑器有三种方式:
        // 1)org.springframework.bean.factory.config.CustomEditorConfigurer配置customEditors,继承PropertyEditorSupport
        //  但是这种方式在4.0之前是可用的,但是4.0之后使用class注册,而非之前的bean方式, 缺点是无法指定如Date的pattern
        // 2)org.springframework.bean.factory.config.CustomEditorConfigurer配置propertyCustomRegistrars,实现PropertyEditorRegistrar接口
        //  这种方式会自己控制注册过程
        // 3)org.springframework.context.support.ConversionServiceFactoryBean配置converters,实现Converter接口
        //  必须指定ConversionServiceFactoryBean的id为conversionService
        TestBean testBean = classPathXmlApplicationContext.getBean(TestBean.class);
        System.out.println(testBean.toString());
        Thread.sleep(2000);
//        classPathXmlApplicationContext.refresh(); // 清空所有注册的bean
        classPathXmlApplicationContext.close();
//        semaphore.acquire();
    }
}
