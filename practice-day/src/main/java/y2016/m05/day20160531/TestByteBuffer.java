package y2016.m05.day20160531;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-31 PM05:22
 */
public class TestByteBuffer {
    @Test
    public void testByteBuffer() {
        // 分配的最大空间是INTEGER.MAX_VALUE,即2^32 = 4GB
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);

        // 游标的位置，默认是0
        // position是下一个put的位置
        System.out.println(byteBuffer.position());

        // 容量在allocate分配决定的，一旦分配，之后就不能再更改
        System.out.println(byteBuffer.capacity());

        System.out.println(byteBuffer.putChar('A'));
        System.out.println(byteBuffer.putChar('A'));

        // 如果put的长度溢出了分配的空间，将抛出java.nio.BufferOverflowException
//        System.out.println(byteBuffer.putChar('A'));

        System.out.println(byteBuffer.position());

        // get方法会将position下移一个，如果当前的position不小于limit，会抛出java.nio.BufferUnderflowException
        // 因为get方法默认是读取当前position的byte
        // 类似有getChar()、getShort()、getInt()、getLong()、getFloat()、getDouble()
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.position());

        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.position());
        byteBuffer.limit(100);

        // 生成新的read_only的byteBuffer,实现类是HeapByteBufferR，
        // 且readOnly = true时，不能调用array方法
        // 正确调用array方法的方式是先使用hasArray来判断
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        // array方法会返回整个capacity长度的byte数组
        System.out.println(byteBuffer.array().length);
        System.out.println(byteBuffer.arrayOffset());

        System.out.println(byteBuffer.compact());

        //        limit = position;
        //        position = 0;
        //        mark = -1;
        //        return this;
        // 将limit限制为当前位置，将position置为第一个位置，清除mark
        // flip的作用限定当前byteBuffer的有效数据区域位置，方便写到channel
        System.out.println(byteBuffer.flip());
        //        position = 0;
        //        limit = capacity;
        //        mark = -1;
        //        return this;
        // 还原为byteBuffer初始化时的状态，如果有数据都是脏数据，没被及时读取，会被覆盖掉
        System.out.println(byteBuffer.clear());

        // 生成一个char类型的buffer视图,视图是从当前position开始到limit
        // 视图的position、limit、markValue与原buffer相互独立的
        // 如果原buffer是direct的，那么视图也是direct的；如果原buffer是read_only的，那么视图也是read_only
        // 类似有asShortBuffer() asIntBuffer() asLongBuffer() asFloatBuffer() asDoubleBuffer()

        // asCharBuffer时，判断ByteBuffer的order生成不同的子类
        // 如果是BIGINDIAN生成ByteBufferAsCharBufferB，否则ByteBufferAsCharBufferL
        // 在初始化的过程中，ByteBufferAsCharBufferB有个byteBuffer bb的属性来保存当前的byteBuffer的引用，将当前byteBuffer的position作为byteBuffer的offset
        // 但是自身的char[] hb是种未被初始化
        // 所以视图在put的时候，其实是put到bb里面去，并未put到charBuffer自身的hb中去
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        System.out.println(charBuffer.capacity());
        System.out.println(charBuffer.limit());
        System.out.println(charBuffer.position());

        // slice和视图很相似，但是底层初始化的方式是不同的，
        // slice初始化还是和ByteBuffer一样的实现类，所以具备了ByteBuffer的所有的特性，有自己的byte[] hb，可以放心使用Array方法
        // 和视图一样，偏移量offset是当前position
        ByteBuffer sliceBuffer = byteBuffer.slice();
        System.out.println("slice:" + sliceBuffer.array());

        charBuffer.put('K');
        charBuffer.put('E');
        charBuffer.put('V');
        charBuffer.put('I');
        charBuffer.put('N');

        System.out.println(charBuffer.position());

        // get方法是从当前position取数据
        System.out.println(charBuffer.get() + ", position :" + charBuffer.position());


        System.out.println(charBuffer.get(0));
        System.out.println(charBuffer.get(1));
        System.out.println(charBuffer.get(2));
        System.out.println(charBuffer.get(3));
        System.out.println(charBuffer.get(4));

        System.out.println(Arrays.toString(byteBuffer.array()));
        System.out.println(charBuffer);

        // array方法的正确用法
        if(charBuffer.hasArray()) {
            System.out.println(Arrays.toString(charBuffer.array()));
        }

        System.out.println(charBuffer.hasArray());


        charBuffer.limit(20);
        System.out.println(charBuffer.limit());

    }

    @Test
    public void testDuplicate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);

//        position = 0;
//        mark = -1;
//        return this;
        // rewind将position、mark重置
        byteBuffer.rewind();

        // duplicate 复制当前的byteBuffer，并共享当前buffer的内容
        // 尽管duplicate创建了新的对象，但是呢
        // duplicate使用当前对象的hb，也就是共享了hb数组的引用，所以才会共享内容
        // 但是其他的position,limit,mark都是独立的，因为这些属性只是标识而已！！
        // 因为共享同一个byte数组，所以两个对象的操作可能会覆盖对方的put
        ByteBuffer duplicateBuffer = byteBuffer.duplicate();
        byteBuffer.putChar('A');

        System.out.println(duplicateBuffer.getChar(0));
        System.out.println(byteBuffer.getChar(0));

        duplicateBuffer.putChar('B');
        System.out.println(duplicateBuffer.getChar(0));
        System.out.println(byteBuffer.getChar(0));

        duplicateBuffer.position(4);
        System.out.println(duplicateBuffer.position());
        System.out.println(byteBuffer.position());



    }
}
