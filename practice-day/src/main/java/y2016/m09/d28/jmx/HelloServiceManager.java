package y2016.m09.d28.jmx;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-28 PM04:30
 */
public class HelloServiceManager {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        HelloService helloService = new HelloService();
        Class<? extends HelloService> helloServiceClass = helloService.getClass();
        ObjectName objectName = new ObjectName(helloServiceClass.getPackage().getName() + ":type=" + helloServiceClass.getSimpleName());
        ObjectInstance objectInstance = platformMBeanServer.registerMBean(helloService, objectName);
        for (MemoryManagerMXBean memoryManagerMXBean : ManagementFactory.getMemoryManagerMXBeans()) {
            System.out.println(ToStringBuilder.reflectionToString(memoryManagerMXBean));
        }
        System.out.println(helloService.getName());
    }
}
