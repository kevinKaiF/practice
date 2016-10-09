package y2016.m07.day20160701;

import org.junit.Test;

import java.util.Scanner;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-07-01 AM11:08
 */
public class TestSystem {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println("running main");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("shutdown main");
//                System.exit(0);
//            }
//        }).start();
//        Thread.currentThread().join();
//        System.out.println(111);
        try(Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String nextLine = scanner.nextLine();
                System.out.println(nextLine);
            }
        }
    }

    @Test
    public void testScanner() {
        B b = new B();
        b.sayName();
        b.say();

    }

    static class A {
        protected static void say() {
            System.out.println("A");
        }

        protected void sayName() {
            say();
            beforeDoSayName();
            doSayName();
        }

        private void beforeDoSayName() {
            System.out.println("A beforeDoSayName");
        }

        protected void doSayName() {
            System.out.println("A doSayName");
        }
    }

    static class B extends A {
        protected static void say() {
            System.out.println("B");
        }
        private void beforeDoSayName() {
            System.out.println("B beforeDoSayName");
        }
        protected void doSayName() {
            System.out.println("B doSayName");
        }
    }
}
