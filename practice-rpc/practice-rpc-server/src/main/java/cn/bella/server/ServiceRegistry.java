package cn.bella.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/1
 */
public class ServiceRegistry {
    private Map<Class, Class> serviceManager;
    public ServiceRegistry() {
        this.serviceManager = new ConcurrentHashMap<>();
    }

    public void registerService(Class<?> superClazz, Class<?> subClazz) {
        if (superClazz.isAssignableFrom(subClazz)) {
            serviceManager.put(superClazz, subClazz);
        } else {
            throw new IllegalArgumentException("接口与实现类不一致");
        }
    }

    public Class getService(Class invokeClass) {
        return serviceManager.get(invokeClass);
    }
}
