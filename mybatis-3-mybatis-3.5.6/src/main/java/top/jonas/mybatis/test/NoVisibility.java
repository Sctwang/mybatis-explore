package top.jonas.mybatis.test;

import org.omg.SendingContext.RunTime;

/**
 * @author Jonas
 * @date 2021/4/25 19:46
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }


    private void test() {

    }
}
