package com.zb.springboot.demo.ftp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {

    private String host;

    private int port;

    private String user;

    private String sftpQueue;

    private String directory;

    private String applicationServerIp;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSftpQueue() {
        return sftpQueue;
    }

    public void setSftpQueue(String sftpQueue) {
        this.sftpQueue = sftpQueue;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getApplicationServerIp() {
        return applicationServerIp;
    }

    public void setApplicationServerIp(String applicationServerIp) {
        this.applicationServerIp = applicationServerIp;
    }
}
