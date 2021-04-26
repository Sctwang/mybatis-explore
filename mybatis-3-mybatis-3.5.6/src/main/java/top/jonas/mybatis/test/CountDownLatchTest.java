package top.jonas.mybatis.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jonas
 * @date 2021/4/26 21:09
 */
public class CountDownLatchTest<K> {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new CountDownLatchTest().test(countDownLatch);
    }

    private void test(CountDownLatch countDownLatch) {
        do {
            System.out.println("countDownLatch: " + countDownLatch.getCount());
            countDownLatch.countDown();
        } while (countDownLatch.getCount() != 0);
    }

}
