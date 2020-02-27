import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


/**
 * @author zhangbo
 * @date 2019-11-26
 */
@Slf4j
public class T {

    public static void main(String[] args) throws Exception {
        while (true) {
            test();
            TimeUnit.SECONDS.sleep(3);
        }
    }

    private static long test() {
        long now = System.currentTimeMillis();
        System.out.println("test : --> " + now);
        return now;
    }
}
