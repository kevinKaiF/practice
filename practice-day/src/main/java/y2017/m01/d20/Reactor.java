package y2017.m01.d20;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM09:26
 */
public class Reactor implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor());
//        selectionKey.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                int select = selector.select();
                System.out.println("select:" + select);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                System.out.println(selectionKeys.size() > 0 ? selectionKeys.iterator().next().readyOps() : -1);
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    dispatcher(iterator.next());
                }
                selectionKeys.clear();
                System.out.println("+");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatcher(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                // 每次连接走一次
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8080);
        reactor.run();
    }
}
