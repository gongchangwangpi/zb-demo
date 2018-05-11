package com.util.pool2;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 负责创建和销毁FTPClient的工厂
 * 
 * @author books
 */
public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
    
    private static final Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);
    
    private FTPConfig ftpConfig;

    public FTPClientFactory(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    public FTPClient create() throws Exception {
        return connect();
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient obj) {
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        
        closeFTPClient(ftpClient);
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        try {
            boolean validate = FTPReply.isPositiveCompletion(ftpClient.noop());
            logger.info("validate FTPClient：" + validate + ", " + ftpClient);
            return validate;
        } catch (IOException e) {
            logger.error("validate FTPClient error", e);
            return false;
        }
    }

    private FTPClient connect() throws IOException {
        FTPClient client = new FTPClient();
        logger.info("FTPClient instance created [FTPClient :" + client + "]");

        FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpClientConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        
        client.configure(ftpClientConfig);
        client.connect(ftpConfig.getIp(), ftpConfig.getPort());

        boolean loginSuccess = client.login(ftpConfig.getUserName(), ftpConfig.getPassword());

        if (!loginSuccess) {
            logger.warn("用户名或密码不匹配,登录失败.");
            client.logout();
            client = null;
        } else {
            // 文件类型,默认是ASCII
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.setControlEncoding("UTF-8");

            // 设置被动模式
            client.enterLocalPassiveMode();
            client.setBufferSize(1024);

            client.setDefaultPort(ftpConfig.getPort());

            client.getReplyString();

            // 响应信息
            int replyCode = client.getReplyCode();

            client.setConnectTimeout(60000);
            client.setDataTimeout(120000);

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                // 释放空间
                closeFTPClient(client);
                logger.warn("连接被拒绝");
                throw new SocketException("连接被拒绝");
            }
        }
        return client;
    }


    private void closeFTPClient(FTPClient ftpClient) {
        try {
            if (ftpClient == null) {
                return;
            }

            logger.info("退出[FTPClient :" + ftpClient + "]");
            //登出
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                logger.info("断开与文件服务器的连接[FTPClient :" + ftpClient + "]");
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (Exception e) {
            logger.error("关闭连接文件服务器的客户端失败", e);
            throw new RuntimeException("关闭连接文件服务器的客户端失败", e);
        }
    }
}
