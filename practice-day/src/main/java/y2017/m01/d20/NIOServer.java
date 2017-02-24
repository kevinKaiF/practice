package y2017.m01.d20;

import java.io.IOException;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2017-01-20 AM10:38
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8080);
        reactor.run();
    }
}
