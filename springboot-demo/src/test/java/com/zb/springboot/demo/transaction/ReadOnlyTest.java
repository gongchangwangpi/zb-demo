package com.zb.springboot.demo.transaction;

import com.alibaba.fastjson.JSON;
import com.zb.springboot.demo.SpringbootDemoApplication;
import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.transaction.ReadOnlyService;
import com.zb.springboot.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class ReadOnlyTest {

	@Autowired
	private ReadOnlyService readOnlyService;

	@Test
//	@Rollback(value = false)
	public void readOnlyTest() {

		User user = readOnlyService.readOnly();

		log.info(JSON.toJSONString(user));

	}

}

