package y2017.m03.d10;

import org.junit.Test;
import sun.nio.ch.DefaultSelectorProvider;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/10
 */
public class TestProvider {
    @Test
    public void testProvider() throws IOException {
        /**
         * provider属性作为缓存，
         * 首先从{@link SelectorProvider#loadProviderFromProperty}，根绝输入参数java.nio.channels.spi.SelectorProvider来查找对应的class，反射实例化
         * 否则从{@link SelectorProvider#loadProviderAsService()}，根据META-INF/service对应的文件，加载配置文件名
         * 否则从{@link DefaultSelectorProvider#create()}，创建WindowsSelectorProvider
         *
         * 注意：DefaultSelectorProvider是不是linux版本和windows版本不同？
         * 答案：是
         * linux版本的DefaultSelectorProvider
         * public static SelectorProvider create() {
         *  String var0 = (String)AccessController.doPrivileged(new GetPropertyAction("os.name"));
         *  return (SelectorProvider)(var0.equals("SunOS")?createProvider("sun.nio.ch.DevPollSelectorProvider"):(var0.equals("Linux")?createProvider("sun.nio.ch.EPollSelectorProvider"):new PollSelectorProvider()));
         * }
         * 而windows版本的DefaultSelectorProvider
         * public static SelectorProvider create() {
         *  return new WindowsSelectorProvider();
         * }
         */
        SelectorProvider provider = SelectorProvider.provider();
        System.out.println(provider.getClass().toString());

        /**
         * ServerSocketChannel创建实际上是由SelectProvider.provider().openServerSocketChannel()提供的
         */
        ServerSocketChannel open = ServerSocketChannel.open();
    }

    @Test
    public void testEntry() {
        Map.Entry[] entries = new Map.Entry[2];
    }

    @Test
    public void testIndex() {
        String[] children = new String[64];
        AtomicInteger childIndex = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            int increment = childIndex.getAndIncrement();
            long st = System.nanoTime();
            int inc = increment & children.length - 1;
            long et = System.nanoTime();
            int abs = Math.abs(increment % children.length);
            long et2 = System.nanoTime();
            System.out.println("inc:" + inc + "(" + (et - st) + "), abs:" + abs + "(" + (et2 - et) + ")");
        }

        System.out.println(isPower(2));
        System.out.println(isPower(3));
        System.out.println(isPower(16));
    }

    public boolean isPower(int value) {
        return (value & -value) == value;
    }

    @Test
    public void testFormat() {
        long st = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            String format = MessageFormat.format("{0} students", 2);
        }
        long et = System.nanoTime();
        System.out.println(et -st);

        st = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            String format1 = String.format("%d students", 2);
        }
        et = System.nanoTime();
        System.out.println(et -st);
    }
}
