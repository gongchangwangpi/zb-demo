package com.invest.legulegu;


import com.alibaba.fastjson.JSON;
import com.invest.HeaderUtil;
import com.invest.entity.LeguWeightBsiDataDO;
import com.invest.handler.LeguWeightBsiPEHandler;
import com.invest.mapper.LeguAvgBsiDataMapper;
import com.invest.mapper.LeguWeightBsiDataMapper;
import com.invest.util.MybatisUtil;
import com.util.http.SimpleHttpClient;
import com.util.http.SimpleHttpsClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 加权PE
 *
 * @author bo6.zhang
 * @date 2021/7/14
 */
public class LeguWeightBsiTest {

    public static void main(String[] args) {

        // 统计说明	等权平均法、中位数法：统计时剔除市盈率小于0与市盈率大于300的股票
        //
        // 市场整体市盈率用于衡量一组股票价格高低的整体水平，通常整体市盈率有三种算法： 总股本加权法、等权平均法、中位数法。
        // 三种算法各有优缺点，总股本加权法容易受到大股本股票的影响，等权平均法也叫算术平均法容易受到微利股票的影响，而中位数法容易受到样本数和样本分布的影响。
        // 总股本加权法是以股票的总股本作为权重，对每只股票的价格和每股收益进行加总计算，即一组股票的总市值除以总收益。
        // 算术平均法是取多只股票市盈率的简单算术平均数。
        // 中位数法则是多只股票市盈率排序后取中间的那个数值。
        LeguWeightMarketIdEnum marketIdEnum = LeguWeightMarketIdEnum.SHANG_ZHENG_50;

        String token = "4eb9b851b8530b95467d0cc81baefd60";

        pullDataFromLegu(marketIdEnum, token);

        LeguWeightBsiPEHandler.analyze(marketIdEnum);

    }

    private static void pullDataFromLegu(LeguWeightMarketIdEnum marketIdEnum, String token) {
//        Map<String, String> header = HeaderUtil.userAgent();
//        SimpleHttpClient.HResp hResp = SimpleHttpsClient.getHRespByHeaders("https://www.legulegu.com/", header);
//        Map<String, String> headers = HeaderUtil.headers(hResp.getHeaders());

        String peResp = pullData(marketIdEnum, token);

        System.out.println(peResp);
        if (StringUtils.isEmpty(peResp)) {
            System.out.println("没有获取到数据");
            return;
        }

        saveInDb(marketIdEnum, peResp);

    }

    private static void saveInDb(LeguWeightMarketIdEnum marketIdEnum, String peResp) {
        LeguWeightBsiResp leguAvgBsiResp = JSON.parseObject(peResp, LeguWeightBsiResp.class);

        List<LeguWeightBsiResp.BsiData> data = leguAvgBsiResp.getData();

        List<LeguWeightBsiDataDO> list = data.stream().map(d -> LeguWeightBsiResp.convert(d, marketIdEnum)).collect(Collectors.toList());

        LeguWeightBsiDataMapper mapper = MybatisUtil.getMapper(LeguWeightBsiDataMapper.class);

        SqlSession sqlSession = MybatisUtil.getSqlSession(ExecutorType.BATCH);

        list.forEach(mapper::insert);

        sqlSession.flushStatements();

        sqlSession.commit();
    }

    private static String pullData(LeguWeightMarketIdEnum marketIdEnum, String token) {
        String url = "https://www.legulegu.com/api/stock-data/weight-pe?marketId=%s&token=%s";
        url = String.format(url, marketIdEnum.getCode(), token);
        return SimpleHttpsClient.get(url);
    }

}
