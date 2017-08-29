package y2017.m05.d02;

import java.util.zip.Checksum;

/**
 * adler-32的算法
 * A = 1 + D1 + D2 + ... + Dn (mod 65521)
 * B = (1 + D1) + (1 + D1 + D2) + ... + (1 + D1 + D2 + ... + Dn) (mod 65521)
 * = n×D1 + (n-1)×D2 + (n-2)×D3 + ... + Dn + n (mod 65521)
 * Adler-32(D) = B × 65536 + A
 *
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/2
 */
public class ChecksumImp implements Checksum {

    // largest prime smaller than 65536
    private final int BASE = 65521;

    // BATCH_MAX is the largest n such that 255n(n+1)/2 + (n+1)(BASE-1) <= 2^32-1
    // 这个表达式我计算出来是5553 ???
    // 批量处理的数目，这个值可以随意，但是选取适当的值可以提高效率
    private final int BATCH_MAX = 5521;

    private long adler = 1;

    @Override
    public void update(int b) {
        adler = updateBytes(adler, b);
    }

    private long updateBytes(long adler, int b) {
        long s1 = adler & 0xffff;
        long s2 = adler >> 16;

        s1 += (byte) b;
        s2 += s1;

        s1 %= BASE;
        s2 %= BASE;

        return (s2 << 16) | s1;
    }

    @Override
    public void update(byte[] b, int off, int len) {
        adler = updateBytes(adler, b, off, len);
    }

    private long updateBytes(long adler, byte[] buf, int off, int len) {
        if (buf == null) {
            return 1L;
        }

        long s1 = adler & 0xffff;
        long s2 = (adler >> 16) & 0xffff;
        int k;

        while (len > 0) {
            k = len < BATCH_MAX ? len : BATCH_MAX;
            len -= k;
            // 取商
            while (k >= 16) {
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                s1 += buf[off++] & 0xff;
                s2 += s1;
                k -= 16;
            }

            // 取余
            while (k > 0) {
                s1 += buf[off++] & 0xff;
                s2 += s1;
                k--;
            }
            s1 %= BASE;
            s2 %= BASE;
        }
        return (s2 << 16) | s1;
    }

    @Override
    public long getValue() {
        return adler;
    }

    @Override
    public void reset() {
        adler = 1;
    }
}
