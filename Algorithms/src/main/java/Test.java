import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * -XX:+UseTLAB -XX:+PrintTLAB -XX:+PrintGCDetails -XX:+UseG1GC
 */
public class Test implements Serializable {

    private static final long serialVersionUID = -1279921928557717157L;

    // 手机号码，1开头的11位数字
    public static final String MOBILE_PATTERN_STR = "^1[0-9]{10}";
    public static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_PATTERN_STR);
    // 银行卡号，16-19位数字
    public static final String BANK_CARD_NO_PATTERN_STR = "[0-9]{16,19}";
    public static final Pattern BANK_CARD_NO_PATTERN = Pattern.compile(BANK_CARD_NO_PATTERN_STR);

    public static void main(String[] args) throws Exception {

        System.out.println(100 >>> 1);
        System.out.println(100 >> 1);
        
        
    }

    
}


