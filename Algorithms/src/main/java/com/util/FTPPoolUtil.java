package com.util;

import java.io.*;
import java.net.SocketException;

import com.test.pool.ObjectPool;
import com.test.pool.ObjectPoolConfig;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP UTIL
 * <p>
 * 利用连接池，不用每次都登出和释放链接，达到链接复用的目的
 */
public final class FTPPoolUtil {

    public static Logger logger = LoggerFactory.getLogger(FTPPoolUtil.class);

    /**
     * FTP 登录用户名
     */
    private static String userName = "jhjHomeImage";

    /**
     * FTP 登录密码
     */
    private static String password = "jhjHomeImage";

    /**
     * FTP 服务器IP地址
     */
    private static String ip = "172.18.8.22";

    /**
     * FTP 端口
     */
    private static int port = 21;

    /**
     * 上传路径
     */
    private static String uploadUrl = "";

    private static ObjectPool pool = new ObjectPool(new ObjectPoolConfig());

    /**
     * 获取FTPClient对象
     *
     * @param middleDir
     * @return
     */
    private static FTPClient getFtpClient(String middleDir) {

//        long start = System.currentTimeMillis();

        FTPClient ftpClient = pool.getFTPClient(-1);

        if (StringUtils.isEmpty(middleDir)) {
            middleDir = "/";
        }
        
        try {
            // change work directory
            if (!ftpClient.changeWorkingDirectory(middleDir)) {
                
                String middleDirTmp = middleDir.endsWith("/") ? removeEnd(middleDir, "/") : middleDir;
                String[] middleDirList = middleDirTmp.split("/");

                String levelDir = uploadUrl;
                for (String subDir : middleDirList) {
                    levelDir = levelDir + "/" + subDir;
                    if (!ftpClient.changeWorkingDirectory(levelDir)) {
                        if (ftpClient.makeDirectory(levelDir))
                            ftpClient.changeWorkingDirectory(levelDir);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        long end = System.currentTimeMillis();
//        logger.info("----->>> getFtpClient {}, use time: {}ms", ftpClient, end - start);

        return ftpClient;
    }


    /**
     * 上传单个文件
     *
     * @param fileName  --本地文件名
     * @param middleDir --扩展中间路径
     * @return true:上传成功;false:上传失败
     */
    public static boolean uploadFile(String fileName, InputStream input, String middleDir) {
        boolean flag = true;
        FTPClient client = null;
        try {
            client = getFtpClient(middleDir);
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            flag = client.storeFile(fileName, input);
        } catch (Exception e) {
            flag = false;
            logger.error("上传文件失败", e);
        } finally {
            returnFTPClient(client);
            closeIO(input);
        }

        return flag;
    }

    /**
     * 下载文件
     *
     * @param remoteFileName -- 服务器上的文件名
     * @param localFileName  -- 本地文件名
     * @return true:下载成功;false:下载失败
     */
    public static boolean loadFile(String remoteFileName, String localFileName) {
        boolean flag = true;
        FTPClient client = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            client = getFtpClient(null);
            fos = new FileOutputStream(localFileName);
            bos = new BufferedOutputStream(fos);
            flag = client.retrieveFile(remoteFileName, bos);
        } catch (Exception e) {
            flag = false;
            logger.error("下载文件失败", e);
        } finally {
            closeIO(bos);
            closeIO(fos);
            returnFTPClient(client);
        }

        return flag;
    }

    /**
     * 下载文件
     * <p>
     * 获取下载目标文件对应的流
     *
     * @param remoteFileName -- 服务器上的文件名
     * @return
     */
    public static InputStream loadFile(String remoteFileName) {
        FTPClient client = null;
        try {
            client = getFtpClient(null);
            return client.retrieveFileStream(remoteFileName);
        } catch (Exception e) {
            logger.error("获取下载文件对应的流失败", e);
        } finally {
            returnFTPClient(client);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @param middleDir
     * @return
     */
    public boolean deleteFile(String fileName, String middleDir) {
        boolean flag = true;
        FTPClient client = null;
        try {
            client = getFtpClient(middleDir);
            flag = client.deleteFile(fileName);
            if (flag) {
                logger.info("删除文件成功");
            } else {
                logger.info("删除文件失败");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("删除文件失败", e);
        } finally {
            returnFTPClient(client);
        }

        return flag;
    }


    /**
     * 删除空目录
     *
     * @param pathName
     */
    public void deleteEmptyDirectory(String pathName) {
        FTPClient client = null;
        try {
            client = getFtpClient(pathName);
            client.removeDirectory(pathName);
        } catch (Exception e) {
            logger.error("删除空目录失败", e);
        } finally {
            returnFTPClient(client);
        }
    }

    /**
     * 列出服务器上目录及文件的名称
     *
     * @param regStr -- 匹配的正则表达式
     */
    public String[] listRemoteFiles(String regStr) {
        String files[] = null;
        FTPClient client = null;
        try {
            client = getFtpClient(regStr);
            files = client.listNames(regStr);
            if (files == null || files.length == 0)
                logger.info("没有匹配到相关的目录和文件");
            else {
                for (int i = 0; i < files.length; i++) {
                    logger.info(files[i]);
                }
            }
        } catch (Exception e) {
            logger.error("列出服务器上目录及文件的名称失败", e);
        } finally {
            returnFTPClient(client);
        }

        return files;
    }

    /**
     * 列出服务器上的所有目录和文件
     */
    public String[] listRemoteAllFiles() {
        String names[] = null;
        FTPClient client = null;
        try {
            client = getFtpClient(uploadUrl);
            names = client.listNames();
            for (int i = 0; i < names.length; i++) {
                logger.info(names[i]);
            }
        } catch (Exception e) {
            logger.error("列出服务器上目录及文件失败", e);
        } finally {
            returnFTPClient(client);
        }

        return names;
    }

    /**
     * 切换到服务器的指定目录
     *
     * @param directory
     */
    public void changeWorkingDirectory(String directory) {
        FTPClient client = getFtpClient(directory);
        try {
            client.changeWorkingDirectory(directory);
        } catch (Exception e) {
            logger.error("切换到指定目录失败", e);
        }
    }

    /**
     * 返回到上一层目录
     */
    public void changeToParentDirectory() {
        FTPClient client = getFtpClient(uploadUrl);
        try {
            client.changeToParentDirectory();
        } catch (Exception e) {
            logger.error("返回上一层目录失败", e);
        }
    }

    /**
     * 重命名文件
     *
     * @param oldFileName --原文件名
     * @param newFileName --新文件名
     */
    public void renameFile(String oldFileName, String newFileName) {
        FTPClient client = getFtpClient(oldFileName);
        try {
            boolean flag = client.rename(oldFileName, newFileName);
            if (flag) {
                logger.info("重命名文件成功[原文件名称:" + oldFileName + ",新文件名称:" + newFileName + "]");
            } else {
                logger.info("重命名文件失败");
            }
        } catch (Exception e) {
            logger.error("重命名文件失败", e);
        } finally {
            returnFTPClient(client);
        }
    }

    /**
     * 获取连接文件服务器的客户端配置
     *
     * @return ftpConfig
     */
    private static FTPClientConfig getFtpClientConfig() {
        FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpClientConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
        return ftpClientConfig;
    }

    /**
     * 在服务器上创建一个文件夹
     *
     * @param dir 文件夹名称,不能含有特殊字符.如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public boolean makeDirectory(String dir) {
        FTPClient client = getFtpClient(dir);
        boolean flag = true;
        try {
            flag = client.makeDirectory(dir);
            if (flag) {
                logger.info("创建文件夹成功");
            } else {
                logger.info("创建文件夹失败");
            }
        } catch (Exception e) {
            flag = false;
            logger.error("创建文件夹失败", e);
        } finally {
            returnFTPClient(client);
        }

        return flag;
    }

    public static void returnFTPClient(FTPClient client) {
        pool.returnFTPClient(client);
    }

    private static void closeIO(Closeable closeable) {
        IOUtils.closeQuietly(closeable);
    }

    public static FTPClient initFtpClient() {

        FTPClient ftpClient = null;
        try {
            // 建立连接
            ftpClient = connect();

            if (ftpClient == null) {
                throw new RuntimeException("登录失败或连接被拒绝");
            }

        } catch (IOException e) {
            logger.error("IO异常", e);
        } catch (Exception e) {
            logger.error("未知异常", e);
        }

        return ftpClient;
    }


    private static FTPClient connect() throws IOException {
        FTPClient client = new FTPClient();
        logger.info("FTPClient instance created [FTPClient :" + client + "]");

        client.configure(getFtpClientConfig());
        client.connect(ip, port);
        if (!client.login(userName, password)) {
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

            client.setDefaultPort(port);

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

    public static void closeFTPClient(FTPClient client) {
        try {
            if (client == null) {
                return;
            }

            logger.info("登出[FTPClient :" + client + "]");
            //登出
            client.logout();
            if (client.isConnected()) {
                logger.info("断开与文件服务器的连接[FTPClient :" + client + "]");
                client.disconnect();
                client = null;
            }
        } catch (Exception e) {
            logger.error("关闭连接文件服务器的客户端失败", e);
            throw new RuntimeException("关闭连接文件服务器的客户端失败", e);
        }
    }

    private static String removeEnd(String str, String remove) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(remove)) {
            return str;
        }

        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }

        return str;
    }
}
