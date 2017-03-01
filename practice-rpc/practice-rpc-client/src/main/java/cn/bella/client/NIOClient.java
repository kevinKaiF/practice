package cn.bella.client;

import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;
import cn.bella.serialize.DeserializeObject;
import cn.bella.serialize.SerializeObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-01-20 AM11:11
 */
public class NIOClient implements Runnable {
    private SocketChannel socketChannel;
    private BlockingQueue<byte[]> requestQueue = new SynchronousQueue<>();
    private Thread sender;
    private Thread receiver;
    private BlockingQueue<ResponseEntity> responseQueue = new SynchronousQueue<>();

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

            responseQueue.offer(DeserializeObject.deserialize(outputStream.toByteArray(), ResponseEntity.class));
//            System.out.println(NIOUtil.RECEIVE_PREFIX + new String(outputStream.toByteArray(), "UTF-8"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void sendMessage(RequestEntity remoteEntity) {
        requestQueue.offer(SerializeObject.serialize(remoteEntity));
    }

    public ResponseEntity getMessage() {
        try {
            return responseQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    class Sender implements Runnable {
        @Override
        public void run() {
            NIOClient.this.send();
        }
    }

    private void send() {
        try {
            byte[] message = requestQueue.take();
            if (message != null) {
                ByteBuffer buffer = ByteBuffer.wrap(message);
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

}
