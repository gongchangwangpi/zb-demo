package com.zb;

import com.alibaba.fastjson.JSON;
import com.zb.fund.FundApplication;
import com.zb.fund.domain.ManUser;
import com.zb.fund.enums.GenderEnum;
import com.zb.fund.enums.JobEnum;
import com.zb.fund.service.user.IManUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FundApplication.class)
public class FundApplicationTests {

	@Autowired
	private IManUserService manUserService;

	@Test
	public void mybatisTypeHandlerTest() {

		ManUser manUser = new ManUser();
		manUser.setUsername("name2");
		manUser.setPassword("123456");
		manUser.setGender(GenderEnum.M.getValue());
		manUser.setJob(JobEnum.ENGINEER.getValue());

		manUserService.save(manUser);

		List<ManUser> userList = manUserService.query();
		System.out.println(JSON.toJSONString(userList));

		manUser.setUsername("name3");
		manUserService.save(manUser);

		userList = manUserService.query();
		System.out.println(JSON.toJSONString(userList));
	}

}

