package com.zb.fund.service.fund.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zb.commons.date.DateUtil;
import com.zb.fund.domain.Fund;
import com.zb.fund.domain.FundTrend;
import com.zb.fund.domain.query.FundTrendQuery;
import com.zb.fund.dto.FundTrendDto;
import com.zb.fund.dto.ResponseDto;
import com.zb.fund.mapper.FundMapper;
import com.zb.fund.mapper.FundTrendMapper;
import com.zb.fund.service.fund.FundTrendService;
import com.zb.fund.service.remote.EastMoneyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangbo
 */
@Slf4j
@Service(value = "fundTrendService")
public class FundTrendServiceImpl implements FundTrendService {
    
    @Autowired
    private FundTrendMapper fundTrendMapper;
    
    @Autowired
    private FundMapper fundMapper;
    
    @Autowired
    private EastMoneyService eastMoneyService;
    
    @Override
    @Transactional
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

    @Override
    public PageInfo<FundTrend> pageList(FundTrendQuery query) {
        if (query.getStatisticsDate() == null) {
            // 默认查询最新的一期
            query.setStatisticsDate(fundTrendMapper.selectLatestStatisticsDate());
        }
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), query.orderBy());
        List<FundTrend> list = fundTrendMapper.selectList(query);
        return new PageInfo<>(list);
    }

    private void saveFundTrends(List<FundTrendDto> dtoList, Date statDate) {
//        List<FundTrend> trendList = new ArrayList<>(dtoList.size());
        Date statisticsDate = null;
        for (FundTrendDto dto : dtoList) {
            FundTrend trend = new FundTrend();
            BeanUtils.copyProperties(dto, trend);
            if (trend.getStatisticsDate() == null) {
                trend.setStatisticsDate(statisticsDate != null ? statisticsDate : statDate);
            } else if (statisticsDate == null) {
                statisticsDate = trend.getStatisticsDate();
            }
            trend.setCreateTime(new Date());
//            trendList.add(trend);

            try {
                fundTrendMapper.insert(trend);
            } catch (DuplicateKeyException e) {
                log.error("【唯一性】保存失败", e);
            }
        }
//        fundTrendMapper.batchInsert(trendList);
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
            fundTrend.setFundCode(getData(detail, 0));
            fundTrend.setFundName(getData(detail, 1));
            fundTrend.setInitial(getData(detail, 2));
            fundTrend.setStatisticsDate(parseDate(getData(detail, 3)));
            fundTrend.setUnitNetWorth(getData(detail, 4));
            fundTrend.setTotalNetWorth(getData(detail, 5));
            fundTrend.setDailyRate(getData(detail, 6));
            fundTrend.setLastWeekRate(getData(detail, 7));
            fundTrend.setLastMonthRate(getData(detail, 8));
            fundTrend.setLast3MonthRate(getData(detail, 9));
            fundTrend.setLast6MonthRate(getData(detail, 10));
            fundTrend.setLastYearRate(getData(detail, 11));
            fundTrend.setLast2YearRate(getData(detail, 12));
            fundTrend.setLast3YearRate(getData(detail, 13));
            fundTrend.setThisYearRate(getData(detail, 14));
            fundTrend.setSinceInceptionRate(getData(detail, 15));
            fundTrend.setSetUpDate(parseDate(getData(detail, 16)));
            fundTrend.setExt17(getData(detail, 17));
            fundTrend.setExt18(getData(detail, 18));
            fundTrend.setExt19(getData(detail, 19));
            fundTrend.setServiceCharge(getData(detail, 20));
            fundTrend.setExt21(getData(detail, 21));
            fundTrend.setExt22(getData(detail, 22));
            fundTrend.setExt23(getData(detail, 23));

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

    private String getData(String[] detail, int index) {
        return detail.length > index ? detail[index] : "";
    }
}
