package y2016.m07.day20160712;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-12 PM03:34
 */
public class Add extends UDF {
    public Integer evaluate(Integer a, Integer b) {
        if (null == a || null == b) {
            return null;
        } return a + b;
    }
    public Double evaluate(Double a, Double b) {
        if (a == null || b == null)
            return null;
        return a + b;
    }
}
