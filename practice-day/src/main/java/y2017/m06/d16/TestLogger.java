package y2017.m06.d16;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/16
 */
public class TestLogger {
    private Logger logger = LoggerFactory.getLogger(TestLogger.class);
    @Test
    public void testLogger() {
        try {
            String s = null;
            s.toString();
        } catch (Exception e) {
            logger.info("test info", e);
            logger.error("test error, params : {}", null, e);
        }
    }

}
