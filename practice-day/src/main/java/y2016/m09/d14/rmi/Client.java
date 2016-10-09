package y2016.m09.d14.rmi;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 PM04:06
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        ServiceConsumer consumer = new ServiceConsumer();

        while (true) {
            HelloService helloService = consumer.lookup(HelloServiceImpl.class.getName());
            if (helloService != null) {
                helloService.sayHello();
            } else {
                System.out.println("there is no service");
            }
            Thread.sleep(1000);
        }
    }
}
