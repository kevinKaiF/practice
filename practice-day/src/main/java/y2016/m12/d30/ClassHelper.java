package y2016.m12.d30;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class ClassHelper {
    public static ClassLoader getCallerClassLoader(Class<?> clazz) {
        return clazz.getClassLoader();
    }
}
