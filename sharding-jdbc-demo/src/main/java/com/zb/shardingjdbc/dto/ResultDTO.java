package com.zb.shardingjdbc.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangbo
 * @date 2020-02-08
 */
@Data
public class ResultDTO implements Serializable {


    private int code;

    private String msg;

    private Object body;

    public ResultDTO() {
    }

    public ResultDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(int code, String msg, Object body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static ResultDTO success() {
        return success(null);
    }

    public static ResultDTO success(Object body) {
        return new ResultDTO(200, "OK", body);
    }

    public static ResultDTO notLogin() {
        return fail(401, "登录超时或未登录,请重新登录");
    }

    public static ResultDTO fail() {
        return fail(500, "Interval Server Error");
    }

    public static ResultDTO fail(String msg) {
        return fail(500, msg);
    }

    public static ResultDTO fail(int code, String msg) {
        return new ResultDTO(code, msg);
    }

}
