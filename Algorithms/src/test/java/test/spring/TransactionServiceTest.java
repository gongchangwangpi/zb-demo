package test.spring;

import com.spring.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by books on 2017/8/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TransactionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Resource
    private TransactionService transactionService;


    @Test
    @Rollback(false)
    public void supports() {
//        transactionService.supports();
        transactionService.required();
    }

}
