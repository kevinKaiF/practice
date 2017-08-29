package y2017.m05.d17;

import org.junit.Test;
import rx.Observable;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/5/17
 */
public class TestRxJava {
    @Test
    public void test1() {
        Observable
                .just("hello world")
                .map(s -> s + "-test")
                .subscribe(s -> System.out.println(s));
    }

    @Test
    public void test2() {
        String s = "hello world";
        System.out.println(s + "-test");
    }

    @Test
    public void testReplace() {
        String s = "000000";
        System.out.println(s.replace("0", "1"));
        System.out.println(s.replaceFirst("0", "2"));
    }


    @Test
    public void test3() {
        getTradingVolume(Double.valueOf(1 * 1000));
        getTradingVolume(Double.valueOf(2 * 1000));
        getTradingVolume(Double.valueOf(1 * 10000));
        getTradingVolume(Double.valueOf(2 * 10000));
        getTradingVolume(Double.valueOf(3 * 10000));
        getTradingVolume(Double.valueOf(4 * 10000));
        getTradingVolume(Double.valueOf(5 * 10000));
        getTradingVolume(Double.valueOf(6 * 10000));
        getTradingVolume(Double.valueOf(7 * 10000));
        getTradingVolume(Double.valueOf(8 * 10000));
        getTradingVolume(Double.valueOf(9 * 10000));
        getTradingVolume(Double.valueOf(10 * 10000));
        getTradingVolume(Double.valueOf(12 * 10000));
        getTradingVolume(Double.valueOf(20 * 10000));
        getTradingVolume(Double.valueOf(26 * 10000));
        getTradingVolume(Double.valueOf(30 * 10000));
        getTradingVolume(Double.valueOf(35 * 10000));
        getTradingVolume(Double.valueOf(40 * 10000));
        getTradingVolume(Double.valueOf(47 * 10000));
        getTradingVolume(Double.valueOf(50 * 10000));
        getTradingVolume(Double.valueOf(62 * 10000));
        getTradingVolume(Double.valueOf(73 * 10000));
        getTradingVolume(Double.valueOf(82 * 10000));
        getTradingVolume(Double.valueOf(98 * 10000));
        System.out.println("百万");
        getTradingVolume(Double.valueOf(100 * 10000));
        getTradingVolume(Double.valueOf(120 * 10000));
        getTradingVolume(Double.valueOf(200 * 10000));
        getTradingVolume(Double.valueOf(240 * 10000));
        getTradingVolume(Double.valueOf(300 * 10000));
        getTradingVolume(Double.valueOf(340 * 10000));
        getTradingVolume(Double.valueOf(400 * 10000));
        getTradingVolume(Double.valueOf(440 * 10000));
        getTradingVolume(Double.valueOf(500 * 10000));
        getTradingVolume(Double.valueOf(550 * 10000));
        getTradingVolume(Double.valueOf(600 * 10000));
        getTradingVolume(Double.valueOf(620 * 10000));
        getTradingVolume(Double.valueOf(700 * 10000));
        getTradingVolume(Double.valueOf(740 * 10000));
        getTradingVolume(Double.valueOf(800 * 10000));
        getTradingVolume(Double.valueOf(880 * 10000));
        getTradingVolume(Double.valueOf(900 * 10000));
        getTradingVolume(Double.valueOf(940 * 10000));
        System.out.println("千万");
        getTradingVolume(Double.valueOf(1000 * 10000));
        getTradingVolume(Double.valueOf(1200 * 10000));
        getTradingVolume(Double.valueOf(1400 * 10000));
        getTradingVolume(Double.valueOf(2000 * 10000));
        getTradingVolume(Double.valueOf(2200 * 10000));
        getTradingVolume(Double.valueOf(2440 * 10000));
        getTradingVolume(Double.valueOf(3000 * 10000));
        getTradingVolume(Double.valueOf(3300 * 10000));
        getTradingVolume(Double.valueOf(4000 * 10000));
        getTradingVolume(Double.valueOf(4400 * 10000));
        getTradingVolume(Double.valueOf(5000 * 10000));
        getTradingVolume(Double.valueOf(5500 * 10000));
        getTradingVolume(Double.valueOf(6000 * 10000));
        getTradingVolume(Double.valueOf(6600 * 10000));
        getTradingVolume(Double.valueOf(7000 * 10000));
        getTradingVolume(Double.valueOf(7700 * 10000));
        getTradingVolume(Double.valueOf(8000 * 10000));
        getTradingVolume(Double.valueOf(8800 * 10000));
        getTradingVolume(Double.valueOf(9000 * 10000));
        getTradingVolume(Double.valueOf(9900 * 10000));
        System.out.println("亿");
        getTradingVolume(Double.valueOf(10000 * 10000));
        getTradingVolume(Double.valueOf(12200 * 10000));
        getTradingVolume(Double.valueOf(14440 * 10000));
        getTradingVolume(Double.valueOf(15555 * 10000));
        getTradingVolume(Double.valueOf(20000 * 10000));
        getTradingVolume(Double.valueOf(23000 * 10000));
        getTradingVolume(Double.valueOf(24000 * 10000));
        getTradingVolume(Double.valueOf(30000 * 10000));
        getTradingVolume(Double.valueOf(40000 * 10000));
        getTradingVolume(Double.valueOf(60000 * 10000));
        getTradingVolume(Double.valueOf(100000 * 10000));
    }

    private void getTradingVolume(Double tradingVolume) {
        System.out.printf(tradingVolume.longValue() + ">>>>>>");
        System.out.println(getTradingVolume1(tradingVolume));
    }


    private String getTradingVolume1(Double tradingVolume) {

        if (tradingVolume == null) {
            tradingVolume = 0d;
        }
        long number = tradingVolume.longValue() / 10000;
        if (number < 1) {
            return "1万以下";
        } else if (number >= 1 && number < 3) {
            return "1万以上";
        } else if (number >= 3 && number < 5) {
            return "3万以上";
        } else if (number >= 5 && number < 8) {
            return "5万以上";
        } else if (number >= 8 && number < 10) {
            return "8万以上";
        } else if (number >= 10 && number < 20) {
            return "10万以上";
        } else if (number >= 20 && number < 30) {
            return "20万以上";
        } else if (number >= 30 && number < 40) {
            return "30万以上";
        } else if (number >= 40 && number < 50) {
            return "40万以上";
        } else if (number >= 20 && number < 30) {
            return "30万以上";
        } else if (number >= 50 && number < 100) {
            return "50万以上";
        } else if (number >= 900 && number < 1000) {
            return "";
        } else if ((number >= 100 && number < 900) || (number >= 1000 && number < 10000)) {
            int length = 0;
            while (number > 10) {
                length++;
                number = number / 10;
            }

            StringBuilder sb = new StringBuilder();
            for (int l = 0; l < length; l++) {
                sb.append("0");
            }

            return number + sb.toString() + "万元以上";
        } else {
            if (number / 10000 >= 2) {
                return "2亿以上";
            } else {
                return "1亿以上";
            }
        }

    }
}
