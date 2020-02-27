import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class StaticTest {

    public static void main(String[] args) {
        staticFunction();
    }


    {
        log.info("2");
    }
    static {
        log.info("1");

//        log.info("static 代码块 b = {}", b);
    }

    static StaticTest st = new StaticTest();


    StaticTest() {
        log.info("3");
        log.info("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {
        log.info("4");
    }

    int a = 110;
    static int b = 112;
}