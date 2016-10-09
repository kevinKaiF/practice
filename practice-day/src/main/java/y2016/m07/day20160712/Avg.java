package y2016.m07.day20160712;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-12 PM04:18
 */
@SuppressWarnings("ALL")
public class Avg extends AbstractGenericUDAFResolver {
    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
            throws SemanticException {
        // Type-checking goes here!
        return new GenericUDAFAvgLong();
    }

    public static class GenericUDAFAvgLong extends GenericUDAFEvaluator {
        @Override
        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            return null;
        }

        @Override
        public void reset(AggregationBuffer aggregationBuffer) throws HiveException {

        }

        @Override
        public void iterate(AggregationBuffer aggregationBuffer, Object[] objects) throws HiveException {

        }

        @Override
        public Object terminatePartial(AggregationBuffer aggregationBuffer) throws HiveException {
            return null;
        }

        @Override
        public void merge(AggregationBuffer aggregationBuffer, Object o) throws HiveException {

        }

        @Override
        public Object terminate(AggregationBuffer aggregationBuffer) throws HiveException {
            return null;
        }
        // UDAF logic goes here!
    }
}

