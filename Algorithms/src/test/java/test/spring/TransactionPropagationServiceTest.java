package test.spring;

import com.spring.service.TransactionPropagationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TransactionPropagationServiceTest {

    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @Test
    @Rollback(false)
    public void propagationTest() {
        transactionPropagationService.required();
    }

}
