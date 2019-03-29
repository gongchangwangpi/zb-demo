package com.zb.fund.job;

import com.zb.fund.service.fund.FundTrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author zhangbo
 */
@Slf4j
@Configuration
public class SyncFundTrendJob {

    @Autowired
    private FundTrendService fundTrendService;
    
//    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60 * 24)
    public void sync() {
        fundTrendService.saveFromEastMoney(new Date());
        log.info("【自动同步数据】===== 同步完成 ===== ");
    }
    
}
