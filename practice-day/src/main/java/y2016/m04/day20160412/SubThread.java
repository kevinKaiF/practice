package y2016.m04.day20160412;

import java.util.concurrent.BlockingQueue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-12 AM11:44
 */
public class SubThread extends MyThread {
    // 如果父类的默认构造方法是私有的，则子类不能创建默认的构造方法
//    private SubThread() {
//
//    }
    public SubThread(BlockingQueue<String> queue) {
        super(queue);
    }
}
