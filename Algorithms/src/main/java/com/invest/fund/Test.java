package com.invest.fund;

import com.util.SimpleHttpClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Test {

    public static void main(String[] args) {

        SimpleHttpClient httpClient = new SimpleHttpClient();
        httpClient.init();
        
// ft 类型 [all / zs指数 / zq债券 / hh混合 / gp股票 / qdii / bb保本]
// sd ed 开始日期，结束日期  yyyy-MM-dd   
// pi 开始页       1   
// pn 每页条数      10000
// st 排序方式      desc
        
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=&tabSubtype=,,,,,&pi";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zs&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=|&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.9352062343681844";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zq&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=|&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.785691537147116";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=hh&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.38358491841283704";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=gp&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.5329698388962159";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=qdii&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.26513837407283836";
//        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=bb&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=|&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.015274557866287175";
        String url = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=zs&rs=&gs=0&sc=qjzf&st=desc&sd=2018-01-25&ed=2019-01-25&qdii=|&tabSubtype=,,,,,&pi=1&pn=10000&dx=1&v=0.9352062343681844";
        
        String response = httpClient.get(url);
        
        log.info(response);

    }
    
}
