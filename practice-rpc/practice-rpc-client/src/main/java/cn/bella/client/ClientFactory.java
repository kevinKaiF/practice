package cn.bella.client;

import java.io.IOException;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/3/3
 */
public class ClientFactory {
    public static Client getClient(boolean async) throws IOException {
        if (async) {
            NIOClient nioClient = new NIOClient("localhost", 8080);
            nioClient.run();
            return nioClient;
        } else {
            AsyncNIOClient asyncNIOClient = new AsyncNIOClient("localhost", 8080);
            asyncNIOClient.run();
            return asyncNIOClient;
        }
    }
}
