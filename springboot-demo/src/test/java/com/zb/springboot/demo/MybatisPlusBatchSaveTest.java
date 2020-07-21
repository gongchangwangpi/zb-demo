package com.zb.springboot.demo;

import com.zb.springboot.demo.entity.User;
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
public class MybatisPlusBatchSaveTest {

	@Autowired
	private UserService userService;

	@Test
	@Rollback(value = false)
	public void saveBatch() {

		List<User> list = new ArrayList<>();

		for (int i = 0; i < 110; i++) {
			User user = new User();
			user.setRegisterTime(LocalDateTime.now());
			user.setCreateTime(new Date());
			user.setAge(i);
			user.setUsername("username" + i);
			list.add(user);
		}

		userService.saveBatch(list, 100);

	}

}

