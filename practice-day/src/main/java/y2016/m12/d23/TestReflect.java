package y2016.m12.d23;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import y2016.m09.d14.Person;

import java.beans.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/23
 */
public class TestReflect {
    @Test
    public void testIntrospect() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
//        System.out.println(ToStringBuilder.reflectionToString(beanInfo, ToStringStyle.MULTI_LINE_STYLE));

        BeanInfo[] additionalBeanInfo = beanInfo.getAdditionalBeanInfo();
//        System.out.println(ToStringBuilder.reflectionToString(additionalBeanInfo, ToStringStyle.MULTI_LINE_STYLE));

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
//        System.out.println(ToStringBuilder.reflectionToString(beanDescriptor, ToStringStyle.MULTI_LINE_STYLE));

        int defaultEventIndex = beanInfo.getDefaultEventIndex();

        int defaultPropertyIndex = beanInfo.getDefaultPropertyIndex();

        EventSetDescriptor[] eventSetDescriptors = beanInfo.getEventSetDescriptors();
//        System.out.println(ToStringBuilder.reflectionToString(eventSetDescriptors, ToStringStyle.MULTI_LINE_STYLE));

        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
//        System.out.println(ToStringBuilder.reflectionToString(methodDescriptors, ToStringStyle.MULTI_LINE_STYLE));
        for (MethodDescriptor methodDescriptor : methodDescriptors) {
            System.out.println("method name : " + methodDescriptor.getName());
            System.out.println(ToStringBuilder.reflectionToString(methodDescriptor.getMethod().getParameterTypes()));
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        System.out.println(ToStringBuilder.reflectionToString(propertyDescriptors, ToStringStyle.MULTI_LINE_STYLE));
    }
}
