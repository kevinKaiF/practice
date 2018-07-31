package y2018.m07.d31;

import java.util.BitSet;
import java.util.Random;

/**
 * TODO
 *
 * @author kevin
 * @since 2018-07-31 11:04
 */
public class FastBloomFilter {

    /**
     * 使用bit位来存储
     */
    private final BitSet bitSet;

    /**
     * bit位的个数
     */
    private final int capacity;

    /**
     * 哈希存储的个数
     */
    private final int[] hashSeeds;


    public FastBloomFilter(int slots, int hashFunctions) {
        bitSet = new BitSet(slots);
        Random r = new Random(System.currentTimeMillis());
        hashSeeds = new int[hashFunctions];
        for (int i = 0; i < hashFunctions; ++i) {
            hashSeeds[i] = r.nextInt();
        }
        capacity = slots;
    }

    public void add(int value) {
        byte[] bytes = new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
        this.add(bytes);
    }

    public void add(String value) {
        byte[] bytes = value.getBytes();
        this.add(bytes);
    }

    public void add(byte[] bytes) {
        for (int i = 0; i < hashSeeds.length; ++i) {
            int h = MurmurHash.hash32(bytes, 4, hashSeeds[i]);
            bitSet.set(Math.abs(h) % capacity, true);
        }
    }

    public void clear() {
        bitSet.clear();
    }

    public boolean contain(int value) {
        byte[] bytes = new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
        return this.contain(bytes);
    }

    public boolean contain(String value) {
        return this.contain(value.getBytes());
    }

    public boolean contain(byte[] bytes) {
        for (int i = 0; i < hashSeeds.length; ++i) {
            int h = MurmurHash.hash32(bytes, 4, hashSeeds[i]);

            if (!bitSet.get(Math.abs(h) % capacity)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        FastBloomFilter bf = new FastBloomFilter(1000, 10);
        System.out.println("Query for 2000: " + bf.contain(2000));
        System.out.println("Adding 2000");
        bf.add(2000);
        System.out.println("Query for 2000: " + bf.contain(2000));

        String str = "kevin test";
        System.out.println(bf.contain(str));
        bf.add(str);
        System.out.println(bf.contain(str));

    }
}
