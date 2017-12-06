package y2017.m11.d22;

import org.junit.Test;
import y2016.m09.d14.Person;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/22
 */
public class TestList {
    @Test
    public void testReflectList() throws IllegalAccessException, InstantiationException {
        Person person;
        Class<ArrayList> arrayListClass = ArrayList.class;
        List arrayList = arrayListClass.newInstance();
        System.out.println(arrayList);

        Method[] declaredMethods = TestList.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            TypeVariable<Method>[] typeParameters = method.getTypeParameters();
            System.out.println(parameterTypes);
        }
    }

    public void set(List<Person> persons, String name, int length) {}

    @Test
    public void testString() {
        String queryUpdatedSql = "id,\n"
                                 + "   `NAME`,\n"
                                 + "   NAME_ENGLISH,\n"
                                 + "   AREA,\n"
                                 + "   ZONE_STR,\n"
                                 + "   ADDRESS,\n"
                                 + "   ADDRESS_EN,\n"
                                 + "   AUTH_STATUS,\n"
                                 + "   AUTH_STATUS2,\n"
                                 + "   AUTH_TYPE,\n"
                                 + "   AUTH_CODE_ID,\n"
                                 + "   AUTH_TIME,\n"
                                 + "   AUTHEN_NUMBER,\n"
                                 + "   `CODE`,\n"
                                 + "   BIDAUTH_EXPIRES,\n"
                                 + "   BIDAUTH_FTIME,\n"
                                 + "   BIDAUTH_STATUS,\n"
                                 + "   BIDAUTH_TIME,\n"
                                 + "   company_site,\n"
                                 + "   WWW_STATION,\n"
                                 + "   COMP_TYPE,\n"
                                 + "   FOUNDED_DATE,\n"
                                 + "   FUND,\n"
                                 + "   FUNDUNIT,\n"
                                 + "   INDUSTRY,\n"
                                 + "   INDUSTRY_STR,\n"
                                 + "   MAIN_PRODUCT,\n"
                                 + "   WORKPATTERN,\n"
                                 + "   TEL,\n"
                                 + "   CONTACT,\n"
                                 + "   CONTACT_EN,\n"
                                 + "   CREATE_DATE,\n"
                                 + "   UPDATE_TIME,\n"
                                 + "   WEB_TYPE,\n"
                                 + "   TENANT_ID,";
        System.out.println(queryUpdatedSql);
    }
}
