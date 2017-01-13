package y2016.m12.d29;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/29
 */
public class MulticastReceiver {
    public static void main(String[] args) {
        MulticastSocket multicastSocket = null;
        DatagramPacket inPacket = null;
        try {
            int bufLen = 32;
            // TODO 如果接收数组不够长，数据会丢失
            byte[] inBuf = new byte[bufLen];
            int PORT = 8888;
            String multicastAddress = "224.2.2.3";

            InetAddress address = InetAddress.getByName(multicastAddress);
            multicastSocket = new MulticastSocket(PORT);
            multicastSocket.joinGroup(address);
            inPacket = new DatagramPacket(inBuf, inBuf.length);

            while (!multicastSocket.isClosed()) {
                multicastSocket.receive(inPacket);
                String message = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("From " + inPacket.getAddress() + " message : " + message);
                Arrays.fill(inBuf, (byte) 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
