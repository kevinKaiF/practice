package y2017.m04.d06;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/14
 */
public class TestDeque {
    @Test
    public void testDeque() {
        // ArrayDeque自动扩容
        Deque<String> deque = new ArrayDeque<>(2);
        deque.add("test1");
        deque.add("test2");
        deque.add("test3");
        deque.add("test4");

        System.out.println(deque);
    }

    @Test
    public void testFinally() {
        getName();
    }

    @Test
    public void testFinally2() {
        getName2();
    }

    // 不管语句是否正常执行，finally总会执行
    private void getName2() {
        try {
           throw new RuntimeException();
        } finally {
            execute();
        }
    }

    /**
     *
     * try...finally中finally块的语句在return关键词返回变量之前执行
     *
     *
     * @return
     */
    public String getName() {
        try {
            return getSimpleName();
        } finally {
            execute();
        }
    }

    private String getSimpleName() {
        System.out.println("simple");
        System.out.println("simple");
        System.out.println("simple");
        System.out.println("simple");
        System.out.println("simple");
        return "simple";
    }

    private void execute() {
        System.out.println("execute");
    }
}
