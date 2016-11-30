package y2016.m11.d04;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/4
 */
public class TestCompiler {
    @Test
    public void testCompiler() {
        JavaCompiler compiler  = ToolProvider.getSystemJavaCompiler();
        System.out.println(compiler == null);
        System.out.println(System.getProperty("java.home"));
    }
}
