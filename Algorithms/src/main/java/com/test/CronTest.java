package com.test;

import org.quartz.CronExpression;
import org.quartz.impl.calendar.CronCalendar;

import java.text.ParseException;
import java.util.Date;

/**
 * cron表达式
 *
 * @author zhangbo
 * @date 2020/4/24
 */
public class CronTest {

    public static void main(String[] args) throws ParseException {

        CronExpression cronExpression = new CronExpression("0 */5 * * * ?");
        System.out.println(new Date());
        System.out.println(cronExpression.getNextValidTimeAfter(new Date()));

    }

}
