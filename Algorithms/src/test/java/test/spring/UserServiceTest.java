//package test.spring;
//
//import com.books.spring.domain.User;
//import com.books.spring.domain.query.UserQuery;
//import com.books.spring.service.TransactionTestService;
//import com.books.spring.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by books on 2017/4/19.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
//
//    @Resource
//    private UserService userService;
//
//    @Resource
//    private TransactionTestService  transactionTestService;
//
////    @Test
////    @Rollback(false)
//    public void test() throws InterruptedException {
////        User user = userService.selectById(1L);
////        System.out.println(JSON.toJSONString(user));
//
////        transactionTestService.readUncommitted();// 不会出现
////        transactionTestService.readCommitted();// 不会出现
//        transactionTestService.repeatableRead();
//
//        TimeUnit.SECONDS.sleep(5);
//
//    }
//
////    @Test
////    @Rollback(false)
//    public void batchSave() {
//        List<User> userList = new ArrayList<>();
//        User u1 = new User("111");
//        User u2 = new User("222");
//        User u3 = new User("3333333333333333333333333");
//        User u4 = new User("444");
//        User u5 = new User("555");
//
//        userList.add(u1);
//        userList.add(u2);
//        userList.add(u3);
//        userList.add(u4);
//        userList.add(u5);
//
//        userService.batchSave(userList);
//    }
//
////    @Test
////    @Rollback(false)
//    public void transTest() {
//        User user = new User();
//        user.setId(1L);
//        user.setAgentName("zhangsan");
//        userService.update1(user);
//    }
//
////    @Test
//    public void save() {
//        User user = new User();
//        user.setAgentName("你好");
//        Long id = userService.save(user);
//        System.out.println(id);
//    }
//
//    @Test
//    public void list() {
//        UserQuery query = new UserQuery();
//        query.setId(1L);
//        List<User> list = userService.selectListByQuery(query);
//        System.out.println(list);
//    }
//
//}
