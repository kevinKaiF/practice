package y2016.m04.day20160413;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-04-13 PM01:30
 */
public class LinkedListTest {
    @Test
    public void testIterator() {
        LinkedList<String> list = new LinkedList<String>();
        // LinkedList的迭代器是通过listIterator方法来实现的，是因为LinkedList继承的是AbstractSequentialList，即有序的List
        // 实际上是ListItr,LinkedList内部类ListItr实现了ListIterator接口。
        //        public ListIterator<E> listIterator(int index) {
        //            checkPositionIndex(index);
        //            return new ListItr(index);
        //        }
        // ListItr构造方法中next是通过node方法来实现的
        //        ListItr(int index) {
        //            // assert isPositionIndex(index);
        //            next = (index == size) ? null : node(index);
        //            nextIndex = index;
        //        }
        // 这里的node使用二分法做个初步的判断，是从表头还是从表尾开始搜索，减少循环次数
        //        Node<E> node(int index) {
        //            // assert isElementIndex(index);
        //
        //            if (index < (size >> 1)) {
        //                Node<E> x = first;
        //                for (int i = 0; i < index; i++)
        //                    x = x.next;
        //                return x;
        //            } else {
        //                Node<E> x = last;
        //                for (int i = size - 1; i > index; i--)
        //                    x = x.prev;
        //                return x;
        //            }
        //        }
        list.iterator();
    }
}
