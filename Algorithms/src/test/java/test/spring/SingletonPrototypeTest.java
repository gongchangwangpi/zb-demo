//package test.spring;
//
//import com.books.spring.service.PrototypeService;
//import com.books.spring.service.PrototypeService2;
//import com.books.spring.service.SingletonService;
//import com.books.spring.service.SingletonService2;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
///**
// * Created by books on 2017/7/24.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//public class SingletonPrototypeTest extends AbstractJUnit4SpringContextTests {
//
//    @Resource
//    private SingletonService singletonService;
//
//    @Resource
//    private SingletonService2 singletonService2;
//
//    @Resource
//    private PrototypeService prototypeService;
//
//    @Resource
//    private PrototypeService2 prototypeService2;
//
//    @Test
//    public void test() {
////        singletonService.sayHello("1");
////        singletonService.sayHello("1");
////
////        System.out.println();
////        prototypeService.sayWorld("2");
////        prototypeService.sayWorld("2");
////
////        System.out.println();
////        singletonService2.sayHello2("3");
////        singletonService2.sayHello2("3");
//
//        System.out.println();
//        prototypeService2.sayWorld2("4");
//        prototypeService2.sayWorld2("4");
//
//    }
//
//}
