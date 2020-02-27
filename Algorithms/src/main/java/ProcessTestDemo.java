import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2019-10-31
 */
public class ProcessTestDemo {

    public static void main(String[] args) {
        Super s = new Sub();
        s.test(s);
    }

    private static class Super {
        public void test(Super s) {
            System.out.println("Super class: super");
        }
        public void test(Sub s) {
            System.out.println("Super class: sub");
        }
    }
    private static class Sub extends Super {
        @Override
        public void test(Super s) {
            System.out.println("Sub class: super");
        }
        @Override
        public void test(Sub s) {
            System.out.println("Sub class: sub");
        }
    }
}
