package com.es.highlevel;

import com.alibaba.fastjson.annotation.JSONField;
import com.bedpotato.alg4.ST;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * @author zhangbo
 * @date 2020/6/12
 */
@Data
public class TraceEntity {

    /**
     * appid
     */
    private String appid;
    /**
     * 用户ID
     */
    private String uid;
    /**
     * request id,由前端生成，与前端埋点数据关联
     */
    private String requestId;
    /**
     * request uri
     */
    private String requestPath;
    /**
     * swagger定义的业务操作
     */
    private String operation;
    /**
     * 行为类型
     */
    private String method;
    /**
     * 方法参数
     */
    private String parameters;
    /**
     * 响应
     */
    private String response;
    /**
     * 自定义属性
     */
    private String properties;
    /**
     * 时间戳
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public String getParameters() {
        // The maximum length of cell contents (text) is 32,767 characters
        if (StringUtils.length(parameters) > 32700) {
            return StringUtils.substring(parameters, 0, 32700);
        }
        return parameters;
    }

    public String getResponse() {
        // The maximum length of cell contents (text) is 32,767 characters
        if (StringUtils.length(response) > 32700) {
            return StringUtils.substring(response, 0, 32700);
        }
        return response;
    }

}
