package com.zb.springboot.demo.track;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Data
public class TrackEvent {

    private String application;
    private String eventType;
    private long timestamp = System.currentTimeMillis();
    private Map<String, Object> attach = new HashMap<>();

}
