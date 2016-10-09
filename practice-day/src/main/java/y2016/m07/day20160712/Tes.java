package y2016.m07.day20160712;


/**
 * The type Tes.
 *
 * @author : kevin
 * @version : Ver1.0
 * @date : 2016-07-13 09:25:15
 */
public class Tes {
    public static enum Mode {
        /**
         * PARTIAL1: 这个是mapreduce的map阶段:从原始数据到部分数据聚合
         * 将会调用iterate()和terminatePartial()
         */
        PARTIAL1,
        /**
         * PARTIAL2: 这个是mapreduce的map端的Combiner阶段，负责在map端合并map的数据::从部分数据聚合到部分数据聚合:
         * 将会调用merge() 和 terminatePartial()
         */
        PARTIAL2,
        /**
         * FINAL: mapreduce的reduce阶段:从部分数据的聚合到完全聚合
         * 将会调用merge()和terminate()
         */
        FINAL,
        /**
         * COMPLETE: 如果出现了这个阶段，表示mapreduce只有map，没有reduce，所以map端就直接出结果了:从原始数据直接到完全聚合
         * 将会调用 iterate()和terminate()
         */
        COMPLETE
    }
}
