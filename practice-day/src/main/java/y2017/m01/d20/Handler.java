package y2017.m01.d20;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM09:43
 */
public class Handler implements Runnable {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final int CAPACITY = 1024;
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(CAPACITY);

    public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private static final int READING = 0;
    private static final int SENDING = 1;
    int state = READING;

    @Override
    public void run() {
        try {
            read();
            selectionKey.attach(new Sender());
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Sender implements Runnable {
        @Override
        public void run() {
            try {
                send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send() throws IOException {
        try {
            if (/*selectionKey.isConnectable() && */selectionKey.isWritable()) {
                String content = "你好客户端_" + System.currentTimeMillis();
                socketChannel.write(ByteBuffer.wrap(content.getBytes(UTF_8)));
                if (outputIsComplete()) {
                    selectionKey.cancel();
                }
                Thread.sleep(5000);
                socketChannel.shutdownOutput();
            }
        } catch (IOException e) {
//            e.printStackTrace();
            selectionKey.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean outputIsComplete() {
        return false;
    }

    private void read() throws IOException {
        try {
            String content = "";
            int bytesRead = socketChannel.read(input);
            while (bytesRead > 0) {
                input.flip();
                while (input.hasRemaining()) {
                    input.get(new byte[input.limit()]);
                    content += new String(input.array(), UTF_8);
                }
                input.clear();
                bytesRead = socketChannel.read(input);
            }
            System.out.println(NIOUtil.RECEIVE_PREFIX + content);
            if (inputIsComplete()) {
                state = SENDING;
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private boolean inputIsComplete() {
        return true;
    }
}
