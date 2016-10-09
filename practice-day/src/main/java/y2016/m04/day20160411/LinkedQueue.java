package y2016.m04.day20160411;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-04-06 PM01:44
 */
public class LinkedQueue<E> {
    private Node<E> head;

    private Node<E> last;

    static class Node<E> {
        E item;

        Node next;

        public Node(E item) {
            this.item = item;
        }
    }

    public LinkedQueue() {
        last = head = new Node<E>(null);
    }

    public void enqueue(Node<E> node) {
        // 1.对于last实际上是last = node，也就说每次enqueue的时候，last只保存当前最新的node
        // 2.对于head, 由于初始化的时候，last = head说明last的引用是指向head的内存地址，每当last修改的时候，head也进行同样的修改
        //   last.next = node,也就是说 head.next = node，
        //   最后一步 last = last.next
        //   第一次 head.next的引用赋值给last
        //   第二次 last.next即head.next.next又赋予新值，接着又将head.next.next赋值last
        //   第三次 last.next即head.next.next.next又赋予新值，接着又将head.next.next.next赋值last
        //   ...
        //   第n次 last.next即head.{n * next}又赋予新值，接着又将head.{n * next}赋值last
//        System.out.println(head == null);
        last.next = node;
//        System.out.println(head == null);
        last = last.next;
//        last = last.next = node;
    }

    public E dequeue() {
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h; // help GC
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    public static void main(String[] args) {
        LinkedQueue<String> linkedQueue = new LinkedQueue<String>();
        linkedQueue.enqueue(new Node<String>("11"));
        System.out.println(linkedQueue.head.next == linkedQueue.last);
        linkedQueue.enqueue(new Node<String>("12"));
        System.out.println(linkedQueue.head.next.next == linkedQueue.last);
        linkedQueue.enqueue(new Node<String>("13"));
        System.out.println(linkedQueue.head.next.next.next == linkedQueue.last);

    }

}
