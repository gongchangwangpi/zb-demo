package com.zb.fund.service.fund.impl;

import com.zb.commons.date.DateUtil;
import com.zb.fund.domain.Fund;
import com.zb.fund.domain.FundTrend;
import com.zb.fund.dto.FundTrendDto;
import com.zb.fund.dto.ResponseDto;
import com.zb.fund.mapper.FundMapper;
import com.zb.fund.mapper.FundTrendMapper;
import com.zb.fund.service.fund.FundTrendService;
import com.zb.fund.service.remote.EastMoneyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangbo
 */
@Service(value = "fundTrendService")
public class FundTrendServiceImpl implements FundTrendService {
    
    @Autowired
    private FundTrendMapper fundTrendMapper;
    
    @Autowired
    private FundMapper fundMapper;
    
    @Autowired
    private EastMoneyService eastMoneyService;
    
    @Override
    public void saveFromEastMoney(Date statDate) {
        // 远程拉取数据
        ResponseDto responseDto = eastMoneyService.get(null, statDate);
        
        // 解析数据
        List<FundTrendDto> dtoList = parseData(responseDto);

        // 和历史数据对比，找出新成立的，保存到数据库
        compareWithHistoricalDataAndSaveNewFunds(dtoList);
        
        // 保存数据
        saveFundTrends(dtoList, statDate);
    }

    private void saveFundTrends(List<FundTrendDto> dtoList, Date statDate) {
        List<FundTrend> trendList = new ArrayList<>(dtoList.size());
        for (FundTrendDto dto : dtoList) {
            FundTrend trend = new FundTrend();
            BeanUtils.copyProperties(dto, trend);
            trend.setStatisticsDate(statDate);
            trend.setCreateTime(new Date());
            trendList.add(trend);
        }
        fundTrendMapper.batchInsert(trendList);
    }

    /**
     * 
     * @param dtoList
     */
    private void compareWithHistoricalDataAndSaveNewFunds(List<FundTrendDto> dtoList) {
        List<String> fundCodeList = fundMapper.selectFundCodeList();

        // 有新成立的
        Set<String> fundCodeSet = new HashSet<>(fundCodeList);
        List<Fund> fundList = new ArrayList<>();
        for (FundTrendDto dto : dtoList) {
            if (fundCodeSet.add(dto.getFundCode())) {
                // 能添加进Set，说明历史数据里面没有
                Fund fund = new Fund();
                BeanUtils.copyProperties(dto, fund);
                // TODO 类型编码和名称
                fund.setFundTypeCode("");
                fund.setFundTypeName("");
                fund.setCreateTime(new Date());
                fundList.add(fund);
            }
        }
        // 保存到数据库
        if (!fundList.isEmpty()) {
            fundMapper.batchInsert(fundList);
        }
    }

    private List<FundTrendDto> parseData(ResponseDto responseDto) {
        List<FundTrendDto> list = new ArrayList<>();
        if (responseDto == null || responseDto.getDatas() == null) {
            return list;
        }

        Map<Integer, String> datas = responseDto.getDatas();
        for (Map.Entry<Integer, String> data : datas.entrySet()) {
            String[] detail = data.getValue().split(",");
            
            FundTrendDto fundTrend = new FundTrendDto();
            fundTrend.setFundCode(getExt(detail, 0));
            fundTrend.setFundName(getExt(detail, 1));
            fundTrend.setInitial(getExt(detail, 2));
            fundTrend.setStatisticsDate(parseDate(getExt(detail, 3)));
            fundTrend.setUnitNetWorth(getExt(detail, 4));
            fundTrend.setTotalNetWorth(getExt(detail, 5));
            fundTrend.setDailyRate(getExt(detail, 6));
            fundTrend.setLastWeekRate(getExt(detail, 7));
            fundTrend.setLastMonthRate(getExt(detail, 8));
            fundTrend.setLast3MonthRate(getExt(detail, 9));
            fundTrend.setLast6MonthRate(getExt(detail, 10));
            fundTrend.setLastYearRate(getExt(detail, 11));
            fundTrend.setLast2YearRate(getExt(detail, 12));
            fundTrend.setLast3YearRate(getExt(detail, 13));
            fundTrend.setThisYearRate(getExt(detail, 14));
            fundTrend.setSinceInceptionRate(getExt(detail, 15));
            fundTrend.setSetUpDate(parseDate(getExt(detail, 16)));
            fundTrend.setExt17(getExt(detail, 17));
            fundTrend.setExt18(getExt(detail, 18));
            fundTrend.setExt19(getExt(detail, 19));
            fundTrend.setServiceCharge(getExt(detail, 20));
            fundTrend.setExt21(getExt(detail, 21));
            fundTrend.setExt22(getExt(detail, 22));
            fundTrend.setExt23(getExt(detail, 23));

            list.add(fundTrend);
        }
        
        return list;
    }
    
    private Date parseDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return DateUtil.defaultParseDate(dateStr);
    }

    private String getExt(String[] detail, int index) {
        return detail.length > index ? detail[index] : "";
    }
}
