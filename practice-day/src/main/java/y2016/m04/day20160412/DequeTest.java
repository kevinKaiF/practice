package y2016.m04.day20160412;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-04-12 PM01:41
 */
public class DequeTest {
//    @Test
    public void testArrayDeque() {
        // ArrayDeque使用数组来实现，最小容量是8，而且容量都是2^n（1.方便扩容;2.方便&时轮回，比如removeFirst）
        // head和tail分别记录当前队首和队尾的游标，两个默认初始值都是0
        // addFirst时，head = (head - 1) & (elements.length - 1)，当head为0时，head = elements.length - 1即最后一个元素
        // removeFirst时，head = (head + 1) & (elements.length - 1)，当head为elements.length - 1时，head = 0
        // removeFirst时，如果队列没有从head添加元素，则就是addLast的第一个元素
        ArrayDeque<String> deque = new ArrayDeque<String>();
        deque.addLast("no");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addLast("yes");
        deque.addFirst("11");
        System.out.println(deque.removeFirst()); // 11
        System.out.println(deque.removeFirst()); // no
    }

//    @Test
    public void testLinkedBlockingDeque() throws InterruptedException {
        // 使用双向链表来实现双端队列，和LinkedBlockingQueue不同，只使用了ReentrantLock一把锁，有notEmpty和notFull两个Condition
        // 分别对task*方法和put*方法以及带有TimeUnit参数的方法进行阻塞
        // 当第一次添加元素，以及删除最后一个元素的的时候，需要维护first节点和last节点的关系
        // 可以创建有界队列
        BlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.push("");
        deque.put("");
    }

    @Test
    public void testNull() {
        if(null instanceof Object) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        System.out.println(Objects.hash(null, null));
//        底层调用Arrays的hash函数
//        public static int hashCode(Object a[]) {
//            if (a == null)
//                return 0;
//
//            int result = 1;
//
//            for (Object element : a)
//                result = 31 * result + (element == null ? 0 : element.hashCode());
//
//            return result;
//        }
    }
}
