package cn.bella.client;

import cn.bella.entity.RequestEntity;
import cn.bella.entity.ResponseEntity;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/3
 */
public interface Client {
    /**
     * launcher client
     */
    void run();

    /**
     * send message
     * @param requestEntity
     */
    void sendMessage(RequestEntity requestEntity);

    /**
     * receive message
     * @return
     */
    ResponseEntity receiveMessage();
}
