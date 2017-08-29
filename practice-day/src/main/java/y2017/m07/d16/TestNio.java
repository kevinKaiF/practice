package y2017.m07.d16;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/16
 */
public class TestNio {
    @Test
    public void testSlice() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putInt(100);
        print(byteBuffer);
        // compact的作用有点裁剪的味道，compact时unsafe会将remain的字节码从0开始复制，之后，会将remain的字节数置为新的position,limit置为capacity
        // 这样就会将position之前的字节冲刷掉
        byteBuffer.compact();
        ByteBuffer slicedBuffer = byteBuffer.slice();
        print(slicedBuffer);
//        byteBuffer.put((byte) 2);

        byteBuffer.flip();
//        System.out.println(byteBuffer.getInt());
//        slicedBuffer.putInt(300);
        print(byteBuffer);
        print(slicedBuffer);
//        slicedBuffer.flip();
//        print(slicedBuffer);
        System.out.println(slicedBuffer.get());
        System.out.println(slicedBuffer.get());
        System.out.println(slicedBuffer.get());
        System.out.println(slicedBuffer.get());
        System.out.println(slicedBuffer.getInt());
    }

    private void print(ByteBuffer byteBuffer) {
        System.out.println("=============================");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.remaining());
        System.out.println(byteBuffer.limit());
    }

    @Test
    public void parseLong() {
        System.out.println(Long.parseLong("f00000001", 16));
        System.out.println(Long.toString(100, 2));
        System.out.println(Long.toString(100, 8));
        System.out.println(Long.toString(100, 16));
    }
}
