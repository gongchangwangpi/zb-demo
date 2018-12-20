package test.spring;

import com.alibaba.fastjson.JSON;
import com.spring.domain.User;
import com.spring.domain.enums.SexEnum;
import com.spring.domain.query.UserQuery;
import com.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by books on 2017/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceTest1 extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private UserService userService;

    @Test
    @Rollback(false)
    public void save() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setSex(SexEnum.MALE);
        user.setAge(88);
        Long id = userService.save(user);
        System.out.println(id);
    }
    
    @Test
    @Rollback(false)
    public void update() {
        User user = new User();
        user.setId(1L);
        user.setUsername("lisi");
        user.setSex(SexEnum.FEMALE);
        user.setAge(99);
        int effectRows = userService.updateUser(user);
        System.out.println(effectRows);
    }

    @Test
    public void list() {
        UserQuery query = new UserQuery();
        List<User> list = userService.selectListByQuery(query);
        System.out.println(JSON.toJSONString(list));
    }

}
