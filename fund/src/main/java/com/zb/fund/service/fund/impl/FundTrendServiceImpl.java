package com.zb.fund.service.fund.impl;

import com.zb.commons.date.DateUtil;
import com.zb.fund.domain.FundTrend;
import com.zb.fund.dto.ResponseDto;
import com.zb.fund.mapper.FundTrendMapper;
import com.zb.fund.service.fund.FundTrendService;
import com.zb.fund.service.remote.EastMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhangbo
 */
@Service(value = "fundTrendService")
public class FundTrendServiceImpl implements FundTrendService {
    
    @Autowired
    private FundTrendMapper fundTrendMapper;
    
    @Autowired
    private EastMoneyService eastMoneyService;
    
    @Override
    public void saveFromEastMoney(Date statDate) {
        ResponseDto responseDto = eastMoneyService.get(null, statDate);
        List<FundTrend> list = parseData(responseDto);
        
        
    }

    private List<FundTrend> parseData(ResponseDto responseDto) {
        List<FundTrend> list = new ArrayList<>();
        if (responseDto == null || responseDto.getDatas() == null) {
            return list;
        }

        String[] datas = responseDto.getDatas();
        for (String data : datas) {
            String[] detail = data.split(",");
            Date setupDate = DateUtil.defaultParseDate(detail[16]);
            Date last2Year = DateUtil.add(new Date(), Calendar.YEAR, -2);


            FundTrend fundTrend = new FundTrend();
                fundTrend.setFundCode(detail[0]);
                fundTrend.setFundName(detail[1]);
                fundTrend.setStatisticsDate(DateUtil.defaultParseDate(detail[3]));
                fundTrend.setUnitNetWorth(detail[4]);
                fundTrend.setTotalNetWorth(detail[5]);
                fundTrend.setDailyRate(detail[6]);
                fundTrend.setLastWeekRate(detail[7]);
                fundTrend.setLastMonthRate(detail[8]);
                fundTrend.setLast3MonthRate(detail[9]);
                fundTrend.setLast6MonthRate(detail[10]);
                fundTrend.setLastYearRate(detail[11]);
                fundTrend.setLast2YearRate(detail[12]);
                fundTrend.setLast3YearRate(detail[13]);
                fundTrend.setThisYearRate(detail[14]);
                fundTrend.setServiceCharge(detail[20]);

                list.add(fundTrend);
        }
        
        return list;
    }
}
