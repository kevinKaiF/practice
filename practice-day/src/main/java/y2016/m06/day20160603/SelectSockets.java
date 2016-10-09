package y2016.m06.day20160603;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-03 PM03:51
 */
public class SelectSockets {
    public static final int PORT = 1234;

    private ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128);

    public static void main(String[] args) {
        new SelectSockets().go();
    }

    private void go() {
        int port = PORT;
        System.out.println("listener port :" + port);

        try {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            ServerSocket serverSocket = serverSocketChannel.socket();

            Selector selector = Selector.open();

            serverSocket.bind(new InetSocketAddress(port));

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int n = selector.select();

                if(n == 0) {
                    continue;
                }

                Iterator iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) iterator.next();

                    // 如果是ServerSocket
                    if(selectionKey.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();

                        SocketChannel socketChannel = serverChannel.accept();

                        registerChannel(selector, socketChannel, SelectionKey.OP_READ);

                        sayHelloChannel(socketChannel);
                    }

                    // 如果是Socket或者Datagram
                    if(selectionKey.isReadable()) {
                        readDataFromSocket(selectionKey);
                    }

                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromSocket(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        int count;

        while ((count = socketChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();

            System.out.println(byteBuffer.get(new byte[count]));

            byteBuffer.clear();

        }

        if(count < 0) {
            socketChannel.close();
        }
    }

    private void sayHelloChannel(SocketChannel socketChannel) throws IOException {
        byteBuffer.clear();
        byteBuffer.put("hello \r\n".getBytes()).flip();

        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }

    }

    private void registerChannel(Selector selector, SocketChannel socketChannel, int ops) throws IOException {
        if(socketChannel == null) {
            return;
        }

        socketChannel.configureBlocking(false);

        socketChannel.register(selector, ops);
    }


}
