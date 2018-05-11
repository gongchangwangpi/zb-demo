package com.spring.domain.echart;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by books on 2017/10/27.
 */
public class OrderQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endData;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    public Date getEndData() {
        return endData;
    }

    public void setEndData(Date endData) {
        this.endData = endData;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
}
