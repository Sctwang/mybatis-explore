package top;

/**
 * @author wyz
 * @date 2021/4/15 22:19
 */
public class jonas {
    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader.toString());
    }
}
