package test.spring;

import com.spring.service.ReadCommitTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by books on 2017/9/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ReadCommitTestServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Resource
    private ReadCommitTestService readCommitTestService;
    
    @Test
    @Rollback(false)
    public void readCommit() {
        readCommitTestService.readCommit();
    }

    @Test
    @Rollback(false)
    public void readRepeatable() {
        readCommitTestService.readRepeatable();
    }
    
    @Test
    @Rollback(false)
    public void phantomRead() {
        readCommitTestService.phantomRead();
    }
    
}
