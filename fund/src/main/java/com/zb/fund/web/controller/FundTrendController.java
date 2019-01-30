package com.zb.fund.web.controller;

import com.zb.commons.date.DateUtil;
import com.zb.commons.dto.RestfulResultDto;
import com.zb.fund.domain.query.FundTrendQuery;
import com.zb.fund.service.fund.FundTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 */
@Validated
@RequestMapping(value = "/fund/trend")
@Controller
public class FundTrendController {
    
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);
    
    @Autowired
    private FundTrendService fundTrendService;
    
    @GetMapping(value = "/sync")
    @ResponseBody
    public RestfulResultDto sync(@NotNull(message = "请选择开始日期") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                 @NotNull(message = "请选择结束日期") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        threadPool.execute(new FetchJob(startDate, endDate));
        
        return RestfulResultDto.succeed("已开始异步获取数据，请稍后");
    }
    
    @GetMapping(value = "/list")
    public String list(FundTrendQuery query, Model model) {
        model.addAttribute("query", query);
        
        return "/fund/trend/list";
    }
    
    class FetchJob implements Runnable {
        private Date startDate;
        private Date endDate;

        FetchJob(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public void run() {
            Date statDate = startDate;
            while (statDate.compareTo(endDate) <= 0) {
                FundTrendController.this.fundTrendService.saveFromEastMoney(statDate);
                statDate = DateUtil.add(statDate, Calendar.DAY_OF_YEAR, 1);
            }
        }
    }
}
