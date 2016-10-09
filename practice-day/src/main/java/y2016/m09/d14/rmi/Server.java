package y2016.m09.d14.rmi;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-14 PM04:58
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
//        if (args.length != 2) {
//            System.out.println("please using command : java Server <rmi_host> <rmi_port>");
//            System.exit(-1);
//        }

        args = new String[]{"192.168.199.104", "8099"};
        ServiceProvider provider = new ServiceProvider();
        HelloService helloService = new HelloServiceImpl();
        provider.publish(helloService, args[0], args[1]);
        Thread.sleep(20000);
    }
}
