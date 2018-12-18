package test.spring;

import com.spring.service.RetryTransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/31 0031.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RetryTransactionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private RetryTransactionService retryTransactionService;

    @Test
    @Rollback(false)
    public void put() {
        System.out.println("----------------------------------------------------- test >>>>>");
        retryTransactionService.retry();
        System.out.println("----------------------------------------------------- test >>>>>");
    }

}
