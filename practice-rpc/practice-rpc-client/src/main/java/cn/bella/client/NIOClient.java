package cn.bella.client;

import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;
import cn.bella.serialize.DeserializeObject;
import cn.bella.serialize.SerializeObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM11:11
 */
public class NIOClient implements Runnable, Client {
    // note : 并没有使用synchronousQueue，会出问题
    private BlockingQueue<ResponseEntity> responseQueue = new ArrayBlockingQueue<>(1);
    private SocketChannel socketChannel;

    public NIOClient(String host, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.connect(new InetSocketAddress(host, port));
    }

    @Override
    public void run() {

    }

    private void receive() {
        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes;
        int count = 0;
        try {
            // socketChannel.read is a block method
            while ((count = socketChannel.read(input)) > 0) {
                input.flip();
                bytes = new byte[count];
                input.get(bytes);
                outputStream.write(bytes);
            }

            responseQueue.offer(DeserializeObject.deserialize(outputStream.toByteArray(), ResponseEntity.class));
            System.out.println("<<<已接收");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void sendMessage(RequestEntity remoteEntity) {
        send(SerializeObject.serialize(remoteEntity));
    }

    @Override
    public ResponseEntity receiveMessage() {
        try {
            receive();
            return responseQueue.poll(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void send(byte[] message) {
        try {
            if (message != null) {
                ByteBuffer buffer = ByteBuffer.wrap(message);
                socketChannel.write(buffer);
                System.out.println(">>>已发送");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
