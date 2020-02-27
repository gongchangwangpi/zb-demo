package com.zb;

import com.alibaba.fastjson.JSON;
import com.zb.fund.FundApplication;
import com.zb.fund.domain.ManUserPlus;
import com.zb.fund.enums.GenderEnum;
import com.zb.fund.enums.JobEnum;
import com.zb.fund.mapper.ManUserPlusMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FundApplication.class)
public class MybatisPlusEnumTest {

	@Autowired
	private ManUserPlusMapper manUserPlusMapper;

	@Test
	public void mybatisTypeHandlerTest() {

		ManUserPlus manUserPlus = new ManUserPlus();

		manUserPlus.setGender(GenderEnum.F);
		manUserPlus.setUsername("plus1");
		manUserPlus.setPassword("plus1");
		manUserPlus.setJob(JobEnum.CODER);

		int id = manUserPlusMapper.insert(manUserPlus);
		log.info("insert id = {}", id);

		ManUserPlus manUserPlus1 = manUserPlusMapper.selectById(id);
		log.info("select user = {}", JSON.toJSONString(manUserPlus1));


	}

}

