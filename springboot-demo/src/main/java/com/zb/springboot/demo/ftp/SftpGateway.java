package com.zb.springboot.demo.ftp;

//import org.springframework.integration.annotation.Gateway;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.file.remote.FileInfo;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.List;

/**
 * @author zhangbo
 * @date 2019-12-17
 */
//@Component
//@MessagingGateway
//public interface SftpGateway {
//
//    /**
//     * 上传文件
//     * @param file
//     */
//    @Gateway(requestChannel = "toSftpChannel")
//    void upload(File file);
//
//    /**
//     * 字节流形式上传文件
//     * @param payload
//     * @param name
//     */
//    @Gateway(requestChannel = "toSftpByteChannel")
//    void upload(@Payload byte[] payload, @Header("name") String name);
//
//    /**
//     * 查看文件是否存在
//     * @param dir
//     * @return
//     */
//    @Gateway(requestChannel = "lsChannel")
//    List<FileInfo> listFile(String dir);
//}
