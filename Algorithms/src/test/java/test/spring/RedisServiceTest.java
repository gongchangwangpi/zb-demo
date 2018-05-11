package test.spring;

import com.spring.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/31 0031.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RedisServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private RedisService redisService;

    @Test
    public void put() {
        redisService.put("testKey", "zhangsan", 60L);

        Object testKey = redisService.get("testKey");

        System.out.println(testKey);
    }

}
