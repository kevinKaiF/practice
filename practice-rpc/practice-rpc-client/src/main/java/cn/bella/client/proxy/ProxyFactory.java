package cn.bella.client.proxy;

import cn.bella.client.Client;
import cn.bella.client.ClientFactory;
import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class ProxyFactory {
    public static <T> T newInstance(final Class<T> clazz) {
        return newInstance(clazz, false);
    }

    public static <T> T newInstance(final Class<T> clazz, final boolean async) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RequestEntity remoteEntity = new RequestEntity(clazz, method.getName(), args);
                Client client = ClientFactory.getClient(async);
                client.sendMessage(remoteEntity);
                ResponseEntity responseEntity = client.receiveMessage();
                if (responseEntity == null) {
                    responseEntity = new ResponseEntity();
                    responseEntity.setSuccess(false);
                    responseEntity.setErrorMsg("time out");
                }

                if (responseEntity.isSuccess()) {
                    return responseEntity.getResult();
                } else {
                    throw new RuntimeException(responseEntity.getErrorMsg());
                }

            }
        });
    }
}
