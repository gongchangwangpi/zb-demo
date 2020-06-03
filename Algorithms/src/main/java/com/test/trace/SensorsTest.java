package com.test.trace;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import java.io.IOException;

/**
 * @author zhangbo
 * @date 2020/5/19
 */
public class SensorsTest {

    public static void main(String[] args) throws Exception {

// 使用 ConcurrentLoggingConsumer 初始化 SensorsAnalytics
        final SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer("您的日志文件路径"));

// 用户的 Distinct ID
        String distinctId = "ABCDEF123456789";

// 记录用户登录事件
        sa.track(distinctId, true, "UserLogin");

// 使用神策分析记录用户行为数据
// ...

// 程序结束前，停止 Java SDK 所有服务
        sa.shutdown();

    }

}
