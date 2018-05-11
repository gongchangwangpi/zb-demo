package com.util.pool2;

import lombok.Getter;
import lombok.Setter;

/**
 * @author books
 */
@Getter
@Setter
public class FTPConfig {

    /** 
     * FTP 登录用户名
     */
    private String userName = "jhjHomeImage";

    /** 
     * FTP 登录密码
     */
    private String password = "jhjHomeImage";

    /** 
     * FTP 服务器IP地址
     */
    private String ip = "172.18.8.22";

    /** 
     * FTP 端口
     */
    private int port = 21;

    /** 
     * 上传路径
     */
    private String uploadUrl = "";
    
}
