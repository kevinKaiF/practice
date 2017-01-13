package y2016.m12.d29;

import java.io.IOException;
import java.net.*;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/29
 */
public class MulticastSender {
    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramPacket = null;
        try {
            byte[] outBuf;
            int count = 0;
            String message = null;
            // multicast底层使用的UDP,所以server端并没有严格限制，地址是否为组播地址
            // multicast组播的地址范围是224.0.0.1-239.255.255.254
            String multicastAddress = "224.2.2.3";
            int PORT = 8888;
            InetAddress address = InetAddress.getByName(multicastAddress);
            datagramSocket = new DatagramSocket();
            while (true) {
                message = "this is multicast!" + count;
                count++;
                outBuf = message.getBytes();
                datagramPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
                datagramSocket.send(datagramPacket);

                System.out.println("server send message : " + message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
