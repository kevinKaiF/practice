package y2017.m01.d20;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM11:11
 */
public class NIOClient implements Runnable {
    private SocketChannel socketChannel;
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private Thread sender;
    private Thread receiver;

    public NIOClient(String host, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.connect(new InetSocketAddress(host, port));
        this.sender = new Thread(new Sender());
        this.receiver = new Thread(new Receiver());
    }

    @Override
    public void run() {
        sender.start();
        receiver.start();
    }

    public void sendMessage(String message) {
        queue.offer(message);
    }

    private void receive() {
        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes;
        int count = 0;
        try {
            while ((count = socketChannel.read(input)) > 0) {
                input.flip();
                bytes = new byte[count];
                input.get(bytes);
                outputStream.write(bytes);
            }

            System.out.println(NIOUtil.RECEIVE_PREFIX + new String(outputStream.toByteArray(), "UTF-8"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    class Sender implements Runnable {
        @Override
        public void run() {
            NIOClient.this.send();
        }
    }

    private void send() {
        try {
            String message = queue.take();
            if (message != null) {
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
                socketChannel.write(buffer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Receiver implements Runnable {
        @Override
        public void run() {
            receive();
        }
    }


    public static void main(String[] args) throws IOException {
        NIOClient nioClient = new NIOClient("localhost", 8080);
        nioClient.run();

        nioClient.sendMessage("200");
    }
}
