package y2016.m05.day20160504;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-04 AM10:30
 */
public class AtomicTest {
    @Test
    public void testLong() {
        AtomicLong atomicLong = new AtomicLong(9);
        System.out.println(atomicLong.getAndDecrement());
        System.out.println(atomicLong.decrementAndGet());
        System.out.println(atomicLong.get());
        // weakCompareAndSet和compareAndSet实现方法竟然是一样的！！！
        // AtomicInteger也是一样
        atomicLong.weakCompareAndSet(1, 1);
        atomicLong.compareAndSet(1, 1);
    }

    @Test
    public void testInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
    }

    @Test
    public void collectionsTest() {
        // Collections.singletonList只包含一个元素
        List list = Collections.singletonList(new ArrayList<String>());
        // new ArrayList(Collection<? extends E> c);
        new ArrayList(list);
    }

    @Test
    public void systemError() {
        // err输出的颜色不同
        System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n\tBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("222");
    }

    @Test
    public void patternTest() {
        Matcher matcher = parse(" '1' '2' '3' ");
        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            // group(0) 匹配整个正则表达式
            // group(n) 匹配第n个子表达式
            System.out.println("0:" + matcher.group(0));
            System.out.println("1:" + matcher.group(1));
        }
    }

    public Matcher parse(String str) {
        Pattern ARGS_PATTERN = Pattern.compile("\\s*([^\"\']\\S*|\"[^\"]*\"|'[^']*')\\s*");
        return ARGS_PATTERN.matcher(str);
    }

    @Test
    public void testInteger2() throws UnknownHostException {
        System.out.println(Integer.parseInt("10", 2));
//        System.out.println(Character.digit('1', 2));
        byte[] bytes = InetAddress.getLocalHost().getAddress();
        System.out.println(Arrays.toString(bytes));
        System.out.println(InetAddress.getLocalHost());
    }



}
