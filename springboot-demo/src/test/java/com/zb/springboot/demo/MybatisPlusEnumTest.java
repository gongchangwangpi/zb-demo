package com.zb.springboot.demo;

import com.zb.springboot.demo.mapper.ManUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class MybatisPlusEnumTest {

//	@Autowired
//	private ManUserMapper manUserMapper;

	@Test
	public void mybatisTypeHandlerTest() {

//		ManUser manUserPlus = new ManUser();
//
//		manUserPlus.setGender(GenderEnum.F);
//		manUserPlus.setUsername("plus1");
//		manUserPlus.setPassword("plus1");
//		manUserPlus.setJob(JobEnum.LEADER);
//
//		int id = manUserMapper.insert(manUserPlus);
//		log.info("insert id = {}", id);

		long id = 1170978372500140037L;

//		ManUser manUserPlus1 = manUserMapper.selectById(id);
//		log.info("select user = {}", JSON.toJSONString(manUserPlus1));


	}

}

