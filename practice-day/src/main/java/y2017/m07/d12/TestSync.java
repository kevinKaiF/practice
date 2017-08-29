package y2017.m07.d12;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/7/12
 */
public class TestSync {
    @Test
    public void testSync() throws InterruptedException {
        String name = "test";
        AtomicBoolean start = new AtomicBoolean(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    start.set(false);
                    synchronized (name) {
                        name.notifyAll();
                    }
                    System.out.println("change start to false");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (name) {
            while (start.get()) {
                name.wait();
                System.out.println("wait finished");
            }
        }
        System.out.println("finish");
    }

    @Test
    public void testString() {
        String str = "SerailNo:[525827021] CreateTime=[1499270773] DispTitle=[UpLink_Down] DispStr=[设备58.216.84.130(编码CZ-LC-OLT.MAN.MA5680T-1;类型宽带接入网接入层-OLT;型号MA5680T)告警: 华为OLT上联口端口Down告警(框号0, 槽位号19, 端口号1)[上联端口描述:]] DevIdEx=[CZ-LC-OLT.MAN.MA5680T-1] DevAddr=[常州网操维数据接入1] City=[常州市区] ResTyp=[宽带接入网接入层-OLT] AckNo=[0] SourceIP=[58.216.84.130] SourceName=[常州潞城Z01/HW-OLT001] Severity=[5] GatherId=[changzhou] UseTo[常州潞城Z01] Port[19|1]";
        System.out.println(Arrays.toString(str.split("]\\s")));
    }

}
