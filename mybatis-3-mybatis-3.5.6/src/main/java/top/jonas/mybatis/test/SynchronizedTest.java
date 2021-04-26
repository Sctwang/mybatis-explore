package top.jonas.mybatis.test;

/**
 * @author Jonas
 * @date 2021/4/26 22:18
 */
public class SynchronizedTest {
    static class Widget {
        public synchronized void doSomething() {
            System.out.println(toString() + ": Widget : doSomething.");
        }
    }

    static class LoggingWidget extends Widget {
        public synchronized void doSomething() {
            System.out.println(toString() + ": LoggingWidget : calling doSomething.");
            super.doSomething();
        }

        public static void main(String[] args) {
            new LoggingWidget().doSomething();
        }
    }
}
