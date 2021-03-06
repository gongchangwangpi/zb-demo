package com.zb.springboot.demo.transaction;

import com.zb.springboot.demo.SpringbootDemoApplication;
import com.zb.springboot.demo.service.transaction.TxIsolationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class TxIsolationTest {

	@Autowired
	private TxIsolationService txIsolationService;

	@Test
//	@Rollback(value = false)
	public void readOnlyTest() {

		txIsolationService.defaultIso();
		txIsolationService.readUncommitted();
		txIsolationService.readCommitted();
		txIsolationService.repeatableRead();
		txIsolationService.serializable();

	}

}

