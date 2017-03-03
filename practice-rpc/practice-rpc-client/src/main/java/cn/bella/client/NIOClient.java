package cn.bella.client;

import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;
import cn.bella.proxy.ProxyFactory;
import cn.bella.serialize.DeserializeObject;
import cn.bella.serialize.SerializeObject;
import cn.bella.service.HelloService;
import cn.bella.service.QueryService;

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
public class NIOClient implements Runnable {
    private SocketChannel socketChannel;
    // note : 并没有使用synchronousQueue
    private BlockingQueue<byte[]> requestQueue = new ArrayBlockingQueue<>(1);
    private Thread sender;
    private Thread receiver;
    private BlockingQueue<ResponseEntity> responseQueue = new ArrayBlockingQueue<>(1);

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

    public void sendMessage(RequestEntity remoteEntity) {
        requestQueue.offer(SerializeObject.serialize(remoteEntity));
    }

    public ResponseEntity getMessage() {
        try {
            return responseQueue.poll(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
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
            byte[] message = requestQueue.take();
            if (message != null) {
                ByteBuffer buffer = ByteBuffer.wrap(message);
                socketChannel.write(buffer);
                System.out.println(">>>已发送");
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

    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.newInstance(HelloService.class);
        String name = helloService.getFullName("client");
        System.out.println(name);

        HelloService helloService1 = ProxyFactory.newInstance(HelloService.class);
        String name1 = helloService1.getFullName("client");
        System.out.println(name1);

        QueryService queryService = ProxyFactory.newInstance(QueryService.class);
        String name2 = queryService.query();
        System.out.println(name2);
    }

}
