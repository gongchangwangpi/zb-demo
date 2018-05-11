/**
 * com.zhangbo.jvm.d9.hotswap包下的测试类
 * <p>
 * Created by Administrator on 2017/5/15 0015.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        int i = 123456789;
        int mask = 255;
        
        System.out.println(i & mask);

        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(mask));
        System.out.println(Integer.toBinaryString(i & mask));

    }
    
}