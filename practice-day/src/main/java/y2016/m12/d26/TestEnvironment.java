package y2016.m12.d26;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;

import java.security.AccessControlException;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/26
 */
public class TestEnvironment {
    @Test
    public void testEnvironment() {
        ApplicationContext ctx = new GenericApplicationContext();
        Environment environment = ctx.getEnvironment();
        boolean foo = environment.containsProperty("foo");
        System.out.println(foo);
    }

    @Test
    public void testMap() {
        System.out.println(getSystemEnvironment());
    }

    public Map<String, Object> getSystemEnvironment() {
        try {
            return (Map) System.getenv();
        }
        catch (AccessControlException ex) {
            System.out.println("2222");
            return (Map) new ReadOnlySystemAttributesMap() {
                @Override
                protected String getSystemAttribute(String attributeName) {
                    try {
                        return System.getenv(attributeName);
                    }
                    catch (AccessControlException ex) {
                        return null;
                    }
                }
            };
        }
    }
}


