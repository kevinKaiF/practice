package cn.bella.server;

import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;
import cn.bella.serialize.DeserializeObject;
import cn.bella.serialize.SerializeObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private ServiceRegistry serviceRegistry;
    ByteBuffer input = ByteBuffer.allocate(CAPACITY);

    public Handler(Selector selector, SocketChannel socketChannel, ServiceRegistry serviceRegistry) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        this.serviceRegistry = serviceRegistry;
        selector.wakeup();
    }

    private static final int READING = 0;
    private static final int SENDING = 1;
    int state = READING;

    @Override
    public void run() {
        try {
            ResponseEntity responseEntity = read();
            selectionKey.attach(new Sender(responseEntity));
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Sender implements Runnable {
        private ResponseEntity responseEntity;
        public Sender(ResponseEntity responseEntity) {
            this.responseEntity = responseEntity;
        }

        @Override
        public void run() {
            try {
                send(responseEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(ResponseEntity responseEntity) throws IOException {
        try {
            if (/*selectionKey.isConnectable() && */selectionKey.isWritable()) {
                socketChannel.write(ByteBuffer.wrap(SerializeObject.serialize(responseEntity)));
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

    private ResponseEntity read() throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead = socketChannel.read(input);
            while (bytesRead > 0) {
                input.flip();
                while (input.hasRemaining()) {
                    input.get(new byte[input.limit()]);
                    byteArrayOutputStream.write(input.array());
                }
                input.clear();
                bytesRead = socketChannel.read(input);
            }

            responseEntity = invoke(byteArrayOutputStream);

            if (inputIsComplete()) {
                state = SENDING;
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            }


        } catch (IOException e) {
            e.printStackTrace();
            handleException(responseEntity, e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            handleException(responseEntity, e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            handleException(responseEntity, e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
            handleException(responseEntity, e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            handleException(responseEntity, e.getMessage());
        }
        return responseEntity;
    }

    private void handleException(ResponseEntity responseEntity, String message) {
        responseEntity.setErrorMsg(message);
        responseEntity.setSuccess(false);
    }

    private ResponseEntity invoke(ByteArrayOutputStream byteArrayOutputStream) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        RequestEntity requestEntity = DeserializeObject.deserialize(byteArrayOutputStream.toByteArray(), RequestEntity.class);
        String methodName = requestEntity.getInvokeMethod();
        Object[] params = requestEntity.getParams();
        Class invokeClass = requestEntity.getInvokeClass();
        Class service = serviceRegistry.getService(invokeClass);
        ResponseEntity responseEntity = new ResponseEntity();
        if (service == null) {
            responseEntity.setSuccess(false);
            responseEntity.setErrorMsg("服务尚未注册");
        } else {
            Object instance = service.newInstance();
            Class[] clazz = null;
            if (params == null) {
                clazz = new Class[0];
            } else {
                clazz = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    clazz[i] = params[i].getClass();
                }
            }
            Method invokedMethod = service.getDeclaredMethod(methodName, clazz);
            Object result = invokedMethod.invoke(instance, params);
            responseEntity.setSuccess(true);
            responseEntity.setResult(result);
        }
        return responseEntity;
    }

    private boolean inputIsComplete() {
        return true;
    }
}
