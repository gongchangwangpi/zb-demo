import com.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.sql.Driver;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * -XX:+UseTLAB -XX:+PrintTLAB -XX:+PrintGCDetails -XX:+UseG1GC
 */
@Slf4j
public class Test implements Serializable {

    private static final long serialVersionUID = -1279921928557717157L;

    // 手机号码，1开头的11位数字
    public static final String MOBILE_PATTERN_STR = "^1[0-9]{10}";
    public static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_PATTERN_STR);
    // 银行卡号，16-19位数字
    public static final String BANK_CARD_NO_PATTERN_STR = "[0-9]{16,19}";
    public static final Pattern BANK_CARD_NO_PATTERN = Pattern.compile(BANK_CARD_NO_PATTERN_STR);

    private static final ThreadLocal<Boolean> couponCount = ThreadLocal.withInitial(() -> {
        System.out.println("local init");
        return false;
    });

    public static void main(String[] args) throws Exception {


//        System.out.println(DateUtils.parseDate("2019-10-31", "yyyy-MM-dd").getTime());
//        System.out.println(DateUtils.parseDate("2019-12-04", "yyyy-MM-dd").getTime());
//
//
//        Date begin = DateUtils.parseDate("20200110000000", "yyyyMMddHHmmss");
//        Date end = DateUtils.parseDate("20200111235959", "yyyyMMddHHmmss");
//
//        System.out.println(dayDiff(begin, end));

        System.out.println(Driver.class.getClassLoader());
        System.out.println(com.mysql.cj.jdbc.Driver.class.getClassLoader());

    }

    private static long dayDiff(Date begin, Date end) {
        return (end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24;
    }

    private static <T> T test(Class<T> type, int value) {
        T[] enumConstants = type.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            if (enumConstant instanceof Value) {
                Value v = (Value) enumConstant;
                if (v.getValue() == value) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    interface Value {
        int getValue();
    }

    @Getter
    @AllArgsConstructor
    enum Type implements Value {
        A(1),
        B(2),
        C(3);

        private int value;
    }

}


