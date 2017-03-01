package cn.bella.proxy;

import cn.bella.client.NIOClient;
import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;

import java.lang.reflect.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class ProxyFactory {
    public static  <T> T newInstance(final Class<T> clazz) {
        return (T)java.lang.reflect.Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RequestEntity remoteEntity = new RequestEntity(clazz, method.getName(), args);
                NIOClient nioClient = new NIOClient("localhost", 8080);
                nioClient.run();
                nioClient.sendMessage(remoteEntity);
                ResponseEntity responseEntity = nioClient.getMessage();
                if (responseEntity.isSuccess()) {
                    return responseEntity.getResult();
                } else {
                    throw new RuntimeException(responseEntity.getErrorMsg());
                }
            }
        });
    }
}
