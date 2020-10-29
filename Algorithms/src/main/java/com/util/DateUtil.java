package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间处理工具类
 * 
 * Created by books on 2017/10/27.
 */
@Slf4j
public class DateUtil {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    private static ThreadLocal<Map<String, SimpleDateFormat>> local = new ThreadLocal(){
        @Override
        protected synchronized Map<String, SimpleDateFormat> initialValue() {
            Map<String, SimpleDateFormat> formatMap = new HashMap<>();
            formatMap.put(DATE_PATTERN, new SimpleDateFormat(DATE_PATTERN));
            formatMap.put(DATETIME_PATTERN, new SimpleDateFormat(DATETIME_PATTERN));
            return formatMap;
        }
    };

    private DateUtil() {
    }
    
    private void destory() {
        local.remove();
    }

    private static void notNull(Object date, String tips) {
        Validate.notNull(date, tips);
    }
    
    private static void notEmpty(String str, String tips) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(tips);
        }
    }

    public static void main(String[] args) {
        Date start = defaultParseDateTime("2020-10-20 00:00:00");
        Date end = defaultParseDateTime("2020-10-29 23:59:59");
        startEndConsume(start, end, date -> DateUtils.addDays(date, 1), (s, e) -> {
            log.info("start = {}, end = {}", defaultFormatDateTime(s), defaultFormatDateTime(e));
        });
    }

    public static void startEndConsume(Date start, Date end, Function<Date, Date> dateFunc, BiConsumer<Date, Date> consumer) {
        notNull(start, "start is null");
        notNull(end, "end is null");

        while (start.compareTo(end) < 0) {
            Date tmpEnd = dateFunc.apply(start);
            tmpEnd = tmpEnd.compareTo(end) <= 0 ? tmpEnd : end;
            consumer.accept(start, tmpEnd);
            start = tmpEnd;
        }
    }

    /**
     *
     * 根据传入的Date对象获取不包含时部的Calendar对象
     *
     * @param date
     * @return
     */
    public static Calendar getClearedTimePartCalendar(Date date) {
        notNull(date, "date is not available");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        clearTimePart(calendar);
        return calendar;
    }

    /**
     *
     * 获取不包含时部的当前Date对象
     *
     * @return
     *
     */
    public static Date getClearedTimePartCurDate(){
        Calendar now = Calendar.getInstance();
        clearTimePart(now);
        return now.getTime();
    }

    /**
     *
     * 是否是同一天
     *
     * @param date
     * @param dateAnother
     * @return
     */
    /*public static boolean isSameDay(Date date, Date dateAnother){
        notNull(date, "date is not available");
        notNull(date, "dateAnother is not available");

        String dateStr = defaultFormatDate(date);
        String dateAnotherStr = defaultFormatDate(dateAnother);
        if(dateStr.equals(dateAnotherStr)){
            return true;
        }
        return false;
    }*/

    /**
     *
     * 是否是周末.即是否是周六或周日
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date){
        notNull(date, "date is not available");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(calendar.get(Calendar.DAY_OF_WEEK) == 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            return true;
        }
        return false;
    }

    /**
     *
     *
     * adds the specified (signed) amount of time to the given calendar field, based on the calendar's rules. 
     *
     * 给指定的Date实例所代表的Calendar实例的field添加指定的时间量,当时间量为负时即代表减少指定的时间量
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("date parameter is not available");
        }

        if (field < 0 || field >= Calendar.ZONE_OFFSET) {
            throw new IllegalArgumentException("field parameter is not available");
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     *
     * 获得指定年份下指定月份的最后一天
     *
     * @param year
     * @param monthInRealWorld 现实世界的月份[1-12]
     * @return
     */
    public static Date getLastDayOfMonthInTheSpecifiedYear(int year, int monthInRealWorld) {
        return getLastDayOfCompMonthInTheSpecifiedYear(year, monthInRealWorld - 1);
    }

    /**
     *
     * 获得指定年份下指定计算机月份的最后一天
     *
     * @param year
     * @param monthInComputer 计算机的月份[0-11]
     * @return
     */
    public static Date getLastDayOfCompMonthInTheSpecifiedYear(int year, int monthInComputer) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, monthInComputer, 1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    /**
     *
     * 获得指定年份下指定月份的第一天
     *
     * @param year
     * @param monthInRealWorld 现实世界的月份[1-12]
     * @return
     */
    public static Date getFirstDayOfMonthInTheSpecifiedYear(int year, int monthInRealWorld) {
        return getFirstDayOfCompMonthInTheSpecifiedYear(year, monthInRealWorld - 1);
    }

    /**
     * 获取当前月的第一天的0时0分0秒
     *
     * @return
     */
    public static Date getFirstDayOfCurMonthWithoutTimePart() {
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtil.getFirstDayOfCompMonthInTheSpecifiedYear(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        return DateUtil.clearTimePart(date);
    }

    /**
     * 获得指定年份下指定计算机月份的第一天
     *
     * @param year
     * @param monthInComputer 计算机的月份[0-11]
     * @return
     */
    public static Date getFirstDayOfCompMonthInTheSpecifiedYear(int year, int monthInComputer) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, monthInComputer, 01);
        return calendar.getTime();
    }

    public static int getYear(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.YEAR);
    }

    public static int getComputerMonth(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.MONTH);
    }

    public static int getRealWorldMonth(Date date) {
        return getComputerMonth(date) + 1;
    }

    public static int getDayForMonth(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.MINUTE);
    }

    public static int getSecond(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.SECOND);
    }
    
    
    public static Date defaultParseDate(String dateStr) {
        notEmpty(dateStr, "dateStr must not empty");
        return parse(local.get().get(DATE_PATTERN), dateStr);
    }
    
    public static Date defaultParseDateTime(String dateStr) {
        notEmpty(dateStr, "dateStr must not empty");
        return parse(local.get().get(DATETIME_PATTERN), dateStr);
    }
    
    public static Date parse(String dateStr, String pattern) {
        notEmpty(dateStr, "dataStr must not empty");
        notEmpty(pattern, "pattern must not empty");
        SimpleDateFormat simpleDateFormat = local.get().computeIfAbsent(pattern, v -> new SimpleDateFormat(pattern));
        return parse(simpleDateFormat, dateStr);
    }
    
    public static Date parse(SimpleDateFormat simpleDateFormat, String dateStr) {
        notNull(simpleDateFormat, "simpleDateFormat must not null");
        notEmpty(dateStr, "dataStr must not empty");
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("dateStr is not available");
        }
    }
    
    
    public static String format(Date date, String pattern) {
        notNull(date, "date must not null");
        notEmpty(pattern, "pattern must not empty");
        SimpleDateFormat simpleDateFormat = local.get().computeIfAbsent(pattern, v -> new SimpleDateFormat(pattern));
        return format(simpleDateFormat, date);
    }
    
    public static String defaultFormatDate(Date date) {
        notNull(date, "date must not null");
        SimpleDateFormat simpleDateFormat = local.get().get(DATE_PATTERN);
        return format(simpleDateFormat, date);
    }
    
    public static String defaultFormatDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = local.get().get(DATETIME_PATTERN);
        return format(simpleDateFormat, date);
    }

    /**
     * 获取今天剩余的毫秒数
     * 
     * @return
     */
    public static long getMillisOfTodayRemains() {
        Date tomorrow = getClearedTimePartCurDate();
        tomorrow = add(tomorrow, Calendar.DAY_OF_MONTH, 1);
        return tomorrow.getTime() - System.currentTimeMillis();
    }
    
    
    private static String format(SimpleDateFormat simpleDateFormat, Date date) {
        return simpleDateFormat.format(date);
    }

    /**
     *
     * 清空Calendar对象的时部.即时,分,秒,毫秒
     *
     * @param calendar
     *
     */
    public static void clearTimePart(Calendar calendar) {

        if (calendar == null) {
            throw new RuntimeException("parameter calendar is not available");
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     *
     * 清空Date对象的时部.即时,分,秒,毫秒
     *
     * @param date
     * @return
     */
    public static Date clearTimePart(Date date) {

        notNull(date, "date is null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
