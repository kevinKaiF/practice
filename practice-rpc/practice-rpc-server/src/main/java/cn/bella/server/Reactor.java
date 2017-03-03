package cn.bella.server;


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
    private final ServiceRegistry serviceRegistry;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serviceRegistry = new ServiceRegistry();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor());
//        selectionKey.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                int select = selector.select();
                System.out.println(select);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    dispatcher(iterator.next());
                }
                selectionKeys.clear();
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

    public void registerService(Class<?> superClazz, Class<?> subClazz) {
        serviceRegistry.registerService(superClazz, subClazz);
    }

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                // 每次连接走一次
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(selector, socketChannel, serviceRegistry);
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
