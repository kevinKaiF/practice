package y2016.m11.d28;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/28
 */
public class TestSelector {
    public void selector() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        // 静态方法创建Channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 配置为非阻塞模式
        ssc.socket().bind(new InetSocketAddress(8080));
        // 将Channel注册到selector
        ssc.register(selector, SelectionKey.OP_ACCEPT); // 注册监听的事件
        while (true) {
            Set<SelectionKey> selectionKeys = selector.keys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) { // 轮询selectionKey
                SelectionKey selectionKey = iterator.next();
                // 判断selectionKey类型
                if (/*(selectionKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT*/selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept(); // 接收到请求
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                } else if (selectionKey.isConnectable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    while (true) {
                        byteBuffer.clear();
                        int n = socketChannel.read(byteBuffer);
                        if (n <= 0) {
                            break;
                        }
                        byteBuffer.flip();
                    }
                    iterator.remove();
                } else if (selectionKey.isReadable()) {

                } else if (selectionKey.isWritable()) {

                } else {
//                    selectionKey.isValid();
                }
            }
        }

    }
}
