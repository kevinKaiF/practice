package y2017.m01.d23;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2017-01-23 PM05:10
 */
public class TestLog {
    @Test
    public void testLog() {
        Logger logger = Logger.getLogger(TestLog.class);
        logger.info("testLog");
    }
}
