package y2016.m09.d21;

import org.junit.Test;

import java.util.BitSet;
import java.util.Random;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-21 PM02:17
 */
public class TestBitSet {
    /**
     * Test bit set.
     * bitset原理，用当前set的值，表示一个64数据index对应的位，超过64的继续扩展出另一个64位
     * 底层有个long数组，每64位记录占据一个long元素，超出64位继续占据下一个long元素
     *
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-09-27 17:16:16
     */
    @Test
    public void testBitSet() {
        BitSet bitSet = new BitSet();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            bitSet.set(random.nextInt(20));
        }

        bitSet.set(65);

        StringBuilder sb = new StringBuilder();
        System.out.println(bitSet.size());
        for (int i = 0; i < bitSet.size(); i++) {
            // 如果当前index的值是set的值，返回为true
            if (bitSet.get(i)) {
                sb.append(i);
            }
        }

        System.out.println(sb.toString());


    }

    @Test
    public void testMoveBit() {
        // -2 (10000000000000000000000000000010)
        // 取反 （11111111111111111111111111111101）
        // +1 (11111111111111111111111111111110)
        // 比如-2（11111111111111111111111111111110）
        System.out.println(-2 >> -1);
        System.out.println(Integer.toBinaryString(-2));
    }

    @Test
    public void testStringFormat() {
        System.out.println(buildJournalName("test", 2));
    }

    private String buildJournalName(String binlogFilePrefix, int middleIndex) {
        return String.format("%s.%06d", binlogFilePrefix, middleIndex);
    }
}
