//package com.test.trace;
//
//import io.growing.sdk.java.GrowingAPI;
//import io.growing.sdk.java.dto.GIOEventMessage;
//
///**
// * @author zhangbo
// * @date 2020/4/28
// */
//public class GrowingIOTest {
//
//    public static void main(String[] args) {
//
//        GIOEventMessage eventMessage = new GIOEventMessage.Builder()
//                .eventTime(System.currentTimeMillis())            // 事件时间，默认为系统时间（选填）
//                .eventKey("BuyProduct")                           // 事件标识 (必填)
//                .loginUserId("417abcabcabcbac")                   // 登录用户ID (必填)
//                .addEventVariable("product_name", "苹果")          // 事件级变量 (选填)
//                .addEventVariable("product_classify", "水果")      // 事件级变量 (选填)
//                .addEventVariable("product_price", 14)            // 事件级变量 (选填)
//                .build();
//
//        //上传事件行为消息到服务器
//        GrowingAPI.send(eventMessage);
//
//    }
//
//}
